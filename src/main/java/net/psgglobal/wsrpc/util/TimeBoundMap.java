package net.psgglobal.wsrpc.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
This file is part of wsrpc.

 wsrpc is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 wsrpc is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with wsrpc.  If not, see <http://www.gnu.org/licenses/>.
 */
public class TimeBoundMap<K, T> implements Map<K, T> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final LinkedHashMap<K, TimestampedValue<T>> hashmap = new LinkedHashMap<>();
	private final long timeLimitMsecs;

	/**
	 * Constructor.
	 * @param timeLimitMsecs The time limit entries will stay in the list
	 */
	public TimeBoundMap(long timeLimitMsecs) {
		this.timeLimitMsecs = timeLimitMsecs;
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
			if (entryValue == null) return false;
			if (entryValue.dated > (System.currentTimeMillis() - timeLimitMsecs)) return true;
			hashmap.remove(key);
			return false;
		}
	}

	@Override
	public boolean containsValue(Object value) {
		synchronized (hashmap) {
			clean();
			for (TimestampedValue<T> entryValue : hashmap.values()) {
				if (!entryValue.value.equals(value)) continue;
				if (entryValue.dated > (System.currentTimeMillis() - timeLimitMsecs)) return true;
				return false;
			}
			return false;
		}
	}

	@Override
	public T get(Object key) {
		synchronized (hashmap) {
			TimestampedValue<T> entryValue = hashmap.get(key);
			if (entryValue == null) return null;
			if (entryValue.dated > (System.currentTimeMillis() - timeLimitMsecs)) return entryValue.value;
			hashmap.remove(key);
			return null;
		}
	}

	@Override
	public T put(K key, T value) {
		if (value == null) throw new IllegalArgumentException();
		synchronized (hashmap) {
			TimestampedValue<T> entryValue = new TimestampedValue<>(value);
			TimestampedValue<T> oldValue = hashmap.put(key, entryValue);
			if (oldValue == null) return null;
			return oldValue.value;
		}
	}

	@Override
	public T remove(Object key) {
		synchronized (hashmap) {
			TimestampedValue<T> oldValue = hashmap.remove(key);
			if (oldValue == null) return null;
			return oldValue.value;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends T> m) {
		synchronized (hashmap) {
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
					if (entry.getValue().dated > purgeBefore) continue;
					itr.remove();
				}
				int endSize = hashmap.size();
				if (endSize < initSize) logger.debug("Removed " + (initSize - endSize) + " expired entries with " + endSize + " left");
			}
		}
	}
}
