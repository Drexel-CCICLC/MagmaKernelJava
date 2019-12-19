package com.meti.exception;

public class DoesNotExistException extends CompileException {
	public DoesNotExistException(String message) {
		super(message);
	}
}
