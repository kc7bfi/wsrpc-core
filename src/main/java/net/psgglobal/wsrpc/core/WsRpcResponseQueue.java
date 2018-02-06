package net.psgglobal.wsrpc.core;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import net.psgglobal.wsrpc.util.TimeBoundMap;

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
 * The wsrpc response queue
 */
public class WsRpcResponseQueue {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final TimeBoundMap<Long, JSONRPC2Response> responses;

	/**
	 * Constructor.
	 */
	public WsRpcResponseQueue() {
		responses = new TimeBoundMap<>(60 * 1000L);
	}

	/**
	 * Constructor
	 * @param ttl the time responses live unread in queue
	 */
	public WsRpcResponseQueue(int ttl) {
		responses = new TimeBoundMap<>(ttl);
	}

	/**
	 * Add a response to the queue
	 * @param requestId the request id
	 * @param response the response
	 */
	public void addResponse(long requestId, JSONRPC2Response response) {
		logger.debug("Received response to {}", requestId);
		synchronized (responses) {
			responses.put(requestId, response);
			responses.notifyAll();
		}
	}

	/**
	 * Get the response on a separate thread
	 * @param requestId the request id to wait for
	 * @param timeout the time to wait for a response (ms)
	 * @return the observable
	 */
	public Single<JSONRPC2Response> onResponse(Object requestId, int timeout) {
		logger.debug("Looking for response to {}", requestId);
		return Single.fromCallable(new ReadResponseCallable(requestId, timeout)).subscribeOn(Schedulers.io());
	}

	/**
	 * Get the response on this thread
	 * @param requestId the request id
	 * @param timeout the timeout value
	 * @return the response
	 */
	public JSONRPC2Response getResponse(Object requestId, int timeout) {
		logger.info("Looking for response to {}", requestId);
		try {
			return new ReadResponseCallable(requestId, timeout).call();
		} catch (Exception e) {
			logger.info("Error reading response: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * Read a response
	 * @param requestId the response id
	 * @param timeout the timeout in milliseconds
	 * @return the response
	 */
	private class ReadResponseCallable implements Callable<JSONRPC2Response> {

		private final Object requestId;
		private final int timeout;

		/**
		 * Constructor.
		 * @param id the response id
		 * @param timeout the read timeout
		 */
		public ReadResponseCallable(Object id, int timeout) {
			this.requestId = id;
			this.timeout = timeout;
		}

		@Override
		public JSONRPC2Response call() throws Exception {
			long readExpires = System.currentTimeMillis() + timeout;
			try {
				while (readExpires > System.currentTimeMillis()) {
					synchronized (responses) {
						JSONRPC2Response response = responses.remove(requestId);
						if (response != null) return response;

						// wait for response to come in
						long waittime = Math.max(readExpires - System.currentTimeMillis(), 100);
						responses.wait(waittime);
					}
				}
				JSONRPC2Response response = responses.remove(requestId);
				if (response != null) return response;
			} catch (InterruptedException e) {
				logger.debug("Wokeup waiting for response");
			}
			throw new IOException("Timeout");
		}
	}
}
