package net.psgglobal.wsrpc.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2002-2017, Prometheus Security Global, Inc.
 */
public class TimeBoundMap<K, T> implements Map<K, T> {

	private static ScheduledExecutorService ThreadPool = Executors
			.newSingleThreadScheduledExecutor(new NamedDaemonThreadFactory("TimeBoundMapCleaner"));

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final LinkedHashMap<K, TimestampedValue<T>> hashmap = new LinkedHashMap<>();
	private final long timeLimitMsecs;
	private long lastCleanTime;

	/**
	 * Constructor.
	 * 
	 * @param timeLimitMsecs
	 *            The time limit entries will stay in the list
	 */
	public TimeBoundMap(long timeLimitMsecs) {
		this.timeLimitMsecs = timeLimitMsecs;
		ThreadPool.scheduleAtFixedRate(() -> clean(), timeLimitMsecs, timeLimitMsecs, TimeUnit.MILLISECONDS);
	}

	@Override
	public int size() {
		synchronized (hashmap) {
			clean();
			return hashmap.size();
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (hashmap) {
			clean();
			return hashmap.isEmpty();
		}
	}

	@Override
	public boolean containsKey(Object key) {
		synchronized (hashmap) {
			TimestampedValue<T> entryValue = hashmap.get(key);
			if (entryValue == null)
				return false;
			if (entryValue.dated > (System.currentTimeMillis() - timeLimitMsecs))
				return true;
			hashmap.remove(key);
			return false;
		}
	}

	@Override
	public boolean containsValue(Object value) {
		synchronized (hashmap) {
			clean();
			for (TimestampedValue<T> entryValue : hashmap.values()) {
				if (!entryValue.value.equals(value))
					continue;
				if (entryValue.dated > (System.currentTimeMillis() - timeLimitMsecs))
					return true;
				return false;
			}
			return false;
		}
	}

	@Override
	public T get(Object key) {
		synchronized (hashmap) {
			TimestampedValue<T> entryValue = hashmap.get(key);
			if (entryValue == null)
				return null;
			if (entryValue.dated > (System.currentTimeMillis() - timeLimitMsecs))
				return entryValue.value;
			hashmap.remove(key);
			return null;
		}
	}

	@Override
	public T put(K key, T value) {
		if (value == null)
			throw new IllegalArgumentException();
		synchronized (hashmap) {
			cleanIf();
			TimestampedValue<T> entryValue = new TimestampedValue<>(value);
			TimestampedValue<T> oldValue = hashmap.put(key, entryValue);
			if (oldValue == null)
				return null;
			return oldValue.value;
		}
	}

	@Override
	public T remove(Object key) {
		synchronized (hashmap) {
			TimestampedValue<T> oldValue = hashmap.remove(key);
			if (oldValue == null)
				return null;
			return oldValue.value;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends T> m) {
		synchronized (hashmap) {
			cleanIf();
			for (Entry<? extends K, ? extends T> entry : m.entrySet()) {
				TimestampedValue<T> entryValue = new TimestampedValue<>(entry.getValue());
				hashmap.put(entry.getKey(), entryValue);
			}
		}
	}

	@Override
	public void clear() {
		synchronized (hashmap) {
			hashmap.clear();
		}
	}

	@Override
	public Set<K> keySet() {
		synchronized (hashmap) {
			clean();
			return hashmap.keySet();
		}
	}

	@Override
	public Collection<T> values() {
		synchronized (hashmap) {
			clean();
			Map<K, T> entries = new HashMap<>();
			for (Entry<K, TimestampedValue<T>> entry : hashmap.entrySet()) {
				entries.put(entry.getKey(), entry.getValue().value);
			}
			return entries.values();
		}
	}

	@Override
	public Set<java.util.Map.Entry<K, T>> entrySet() {
		synchronized (hashmap) {
			clean();
			Map<K, T> entries = new HashMap<>();
			for (Entry<K, TimestampedValue<T>> entry : hashmap.entrySet()) {
				entries.put(entry.getKey(), entry.getValue().value);
			}
			return entries.entrySet();
		}
	}

	private class TimestampedValue<TT> {
		private final TT value;
		private final long dated = System.currentTimeMillis();

		/**
		 * Constructor
		 * 
		 * @param value
		 *            the value
		 */
		public TimestampedValue(TT value) {
			this.value = value;
		}
	}

	/**
	 * Check to see if we have not cleaned in a while
	 */
	private void cleanIf() {
		if (lastCleanTime + timeLimitMsecs * 0.1 < System.currentTimeMillis())
			clean();
	}

	/**
	 * Clean the list of expired entries
	 */
	private void clean() {
		synchronized (hashmap) {
			long purgeBefore = System.currentTimeMillis() - timeLimitMsecs;
			synchronized (hashmap) {
				int initSize = hashmap.size();
				Iterator<Entry<K, TimestampedValue<T>>> itr = hashmap.entrySet().iterator();
				while (itr.hasNext()) {
					Entry<K, TimestampedValue<T>> entry = itr.next();
					if (entry.getValue().dated > purgeBefore)
						continue;
					itr.remove();
				}
				int endSize = hashmap.size();
				if (endSize < initSize)
					logger.debug("Removed " + (initSize - endSize) + " expired entries with " + endSize + " left");
			}
		}
		lastCleanTime = System.currentTimeMillis();
	}

}
