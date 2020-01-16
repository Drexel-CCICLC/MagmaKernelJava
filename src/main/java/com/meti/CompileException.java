package com.meti;

class CompileException extends RuntimeException {
	CompileException(String message, Throwable cause) {
		super(message, cause);
	}

	CompileException(String message) {
		super(message);
	}
}
