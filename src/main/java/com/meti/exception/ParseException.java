package com.meti.exception;

public class ParseException extends CompileException {
	private static final long serialVersionUID = -1722623904619436942L;

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
