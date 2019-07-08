package net.psgglobal.wsrpc.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
 * Composit cache of futures
 */
public class WsRpcCompositFuture {

	private List<Future<?>> futures = new LinkedList<>();

	/**
	 * Constructor
	 */
	public WsRpcCompositFuture() {
	}

	public void add(Future<?> future) {
		synchronized (futures) {
			clean();
			futures.add(future);
		}
	}

	/**
	 * Cancel remaining futures
	 * @param mayInterruptIfRunning if true, then we can interrupt a running task
	 */
	public void cancelRemaining(boolean mayInterruptIfRunning) {
		synchronized (futures) {
			Iterator<Future<?>> iter = futures.iterator();
			while (iter.hasNext()) {
				Future<?> future = iter.next();
				if (future.isCancelled() || future.isDone()) continue;
				future.cancel(mayInterruptIfRunning);
			}
			futures.clear();
		}
	}

	/**
	 * Wait for all futures to complete
	 * @param timeout the timeout
	 * @param timeUnit the timeout units
	 * @throws InterruptedException error
	 * @throws ExecutionException error
	 * @throws TimeoutException error
	 */
	public void waitForAll(int timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
		synchronized (futures) {
			Iterator<Future<?>> iter = futures.iterator();
			while (iter.hasNext()) {
				Future<?> future = iter.next();
				if (future.isCancelled() || future.isDone()) continue;
				future.get(timeout, timeUnit);
			}
		}
	}

	/**
	 * Clean the list of closed futures
	 */
	private void clean() {
		synchronized (futures) {
			Iterator<Future<?>> iter = futures.iterator();
			while (iter.hasNext()) {
				Future<?> future = iter.next();
				if (future.isCancelled() || future.isDone()) iter.remove();
			}
		}
	}
}
