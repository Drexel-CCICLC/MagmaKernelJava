package com.meti.exception;

public class StateException extends CompileException {
	private static final long serialVersionUID = -1315938955815895073L;

	StateException(String message, Throwable cause) {
		super(message, cause);
	}

	public StateException(String message) {
		super(message);
	}
}
