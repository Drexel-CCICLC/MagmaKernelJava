package com.meti.exception;

public class CompileException extends RuntimeException {
	private static final long serialVersionUID = 5567097799668238843L;

	CompileException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompileException(String message) {
		super(message);
	}
}
