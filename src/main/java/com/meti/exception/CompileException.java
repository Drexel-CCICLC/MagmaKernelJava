package com.meti.exception;

class CompileException extends RuntimeException {
	private static final long serialVersionUID = 5567097799668238843L;

	CompileException(String message, Throwable cause) {
		super(message, cause);
	}

	CompileException(String message) {
		super(message);
	}
}
