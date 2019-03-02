package com.rpc.zw.netty.exception;

public abstract class BaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int errorId;

	protected BaseException(int errorId) {
		super();
		this.setErrorId(errorId);
	}

	protected BaseException(int errorId, String errorMsg) {
		super(errorMsg);
		this.errorId = errorId;
	}

	protected BaseException(int errorId, String errorMsg, Throwable cause) {
		super(errorMsg, cause);
		this.errorId = errorId;
	}

	protected BaseException(int errorId, Throwable cause) {
		super(cause);
		this.errorId = errorId;
	}

	protected int getErrorId() {
		return errorId;
	}

	protected void setErrorId(int errorId) {
		this.errorId = errorId;
	}
}
