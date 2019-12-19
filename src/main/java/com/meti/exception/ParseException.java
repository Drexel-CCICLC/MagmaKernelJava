package com.meti.exception;

public class ParseException extends CompileException {
	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseException(String message) {
		super(message);
	}
}
