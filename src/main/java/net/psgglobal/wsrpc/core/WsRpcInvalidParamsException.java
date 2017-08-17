package net.psgglobal.wsrpc.core;

/**
 * Copyright (c) 2002-2017, Prometheus Security Global, Inc.
 */
public class WsRpcInvalidParamsException extends WsRpcException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public WsRpcInvalidParamsException() {
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            the error message
	 */
	public WsRpcInvalidParamsException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            the causing error
	 */
	public WsRpcInvalidParamsException(Throwable cause) {
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
	public WsRpcInvalidParamsException(String message, Throwable cause) {
		super(message, cause);
	}

}
