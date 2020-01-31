package com.meti.exception;

public class CompileException extends RuntimeException {
    public CompileException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompileException(String message) {
		super(message);
	}
}
