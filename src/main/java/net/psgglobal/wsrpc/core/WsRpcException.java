package net.psgglobal.wsrpc.core;

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
public class WsRpcException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public WsRpcException() {
	}

	/**
	 * Constructor
	 * @param message the error message
	 */
	public WsRpcException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * @param cause  the causing error
	 */
	public WsRpcException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 * @param message the error message
	 * @param cause the causing error
	 */
	public WsRpcException(String message, Throwable cause) {
		super(message, cause);
	}
}
