package net.psgglobal.wsrpc.util;

import java.util.concurrent.ThreadFactory;

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
 * Thread factory that creates named daemon threads.
 */
public class NamedDaemonThreadFactory implements ThreadFactory {

	private final String name;
	private int count;

	/**
	 * Constructor.
	 * @param name the name of the theads
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
