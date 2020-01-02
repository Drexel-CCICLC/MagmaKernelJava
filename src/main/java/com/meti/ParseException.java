package com.meti;

public class ParseException extends RuntimeException {
	private static final String HEADER = "Failed to parse: ";

	public ParseException(String value, Throwable e) {
		super(HEADER + value, e);
	}

	public ParseException(String value) {
		super(HEADER + value);
	}
}
