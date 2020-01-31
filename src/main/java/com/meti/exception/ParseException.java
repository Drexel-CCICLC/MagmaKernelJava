package com.meti.exception;

public class ParseException extends CompileException {
	public ParseException(String message) {
		super(message);
	}

    public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
