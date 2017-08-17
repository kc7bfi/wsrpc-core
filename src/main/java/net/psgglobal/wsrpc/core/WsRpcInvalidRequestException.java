package net.psgglobal.wsrpc.core;

/**
 * Copyright (c) 2002-2017, Prometheus Security Global, Inc.
 */
public class WsRpcInvalidRequestException extends WsRpcException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public WsRpcInvalidRequestException() {
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            the error message
	 */
	public WsRpcInvalidRequestException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            the causing error
	 */
	public WsRpcInvalidRequestException(Throwable cause) {
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
	public WsRpcInvalidRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
