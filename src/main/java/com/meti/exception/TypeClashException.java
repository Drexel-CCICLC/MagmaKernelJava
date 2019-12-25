package com.meti.exception;

import com.meti.type.Type;

public class TypeClashException extends TypeException {
	public TypeClashException(Type expected, Type actual) {
		this("Expected type of " + expected + " but was actually " + actual);
	}

	public TypeClashException(String message) {
		super(message);
	}
}
