package net.psgglobal.wsrpc.core;

/**
 * Copyright (c) 2002-2017, Prometheus Security Global, Inc.
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
	 * 
	 * @param message
	 *            the error message
	 */
	public WsRpcException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            the causing error
	 */
	public WsRpcException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the error message
	 * @param cause
	 *            the causing error
	 */
	public WsRpcException(String message, Throwable cause) {
		super(message, cause);
	}

}
