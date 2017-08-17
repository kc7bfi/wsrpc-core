package net.psgglobal.wsrpc.core;

/**
 * Copyright (c) 2002-2017, Prometheus Security Global, Inc.
 */
public class WsRpcInternalErrorException extends WsRpcException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public WsRpcInternalErrorException() {
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            the error message
	 */
	public WsRpcInternalErrorException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            the causing error
	 */
	public WsRpcInternalErrorException(Throwable cause) {
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
	public WsRpcInternalErrorException(String message, Throwable cause) {
		super(message, cause);
	}

}
