package net.psgglobal.wsrpc.util;

import java.util.concurrent.ThreadFactory;

/**
 * Copyright (c) 2002-2017, Prometheus Security Global, Inc.
 */
public class NamedDaemonThreadFactory implements ThreadFactory {

	private final String name;
	private int count;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of the theads
	 */
	public NamedDaemonThreadFactory(String name) {
		this.name = name;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		count++;
		Thread thread = new Thread(runnable, name + "-" + count);
		thread.setDaemon(true);
		return thread;
	}

}
