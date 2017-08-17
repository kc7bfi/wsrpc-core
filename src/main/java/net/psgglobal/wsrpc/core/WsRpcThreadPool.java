package net.psgglobal.wsrpc.core;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.psgglobal.wsrpc.util.NamedDaemonThreadFactory;

/**
 * Copyright (c) 2002-2017, Prometheus Security Global, Inc.
 */
public final class WsRpcThreadPool extends ThreadPoolExecutor {

	private static WsRpcThreadPool TheInstance;
	private static ThreadFactory TheThreadFactory = new NamedDaemonThreadFactory("wsrpc");

	/**
	 * Constructor
	 */
	private WsRpcThreadPool() {
		super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), TheThreadFactory);
	}

	/**
	 * @return the singleton instance
	 */
	public static synchronized WsRpcThreadPool getInstance() {
		if (TheInstance == null)
			TheInstance = new WsRpcThreadPool();
		return TheInstance;
	}
}
