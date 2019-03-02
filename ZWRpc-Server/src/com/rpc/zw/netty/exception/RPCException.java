package com.rpc.zw.netty.exception;

public class RPCException extends Exception {

	private static final long serialVersionUID = 1L;

	public RPCException() {
		super();
	}

	public RPCException(String errorMsg) {
		super(errorMsg);
	}

	public RPCException(Throwable cause) {
		super(cause);
	}

	public RPCException(String errorMsg, Throwable cause) {
		super(errorMsg, cause);
	}
}
