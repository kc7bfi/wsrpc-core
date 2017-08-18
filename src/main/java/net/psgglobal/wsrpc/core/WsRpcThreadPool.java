package net.psgglobal.wsrpc.core;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.psgglobal.wsrpc.util.NamedDaemonThreadFactory;

/*
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

/**
 * A common wsrpc thread pool.
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
		if (TheInstance == null) TheInstance = new WsRpcThreadPool();
		return TheInstance;
	}
}
