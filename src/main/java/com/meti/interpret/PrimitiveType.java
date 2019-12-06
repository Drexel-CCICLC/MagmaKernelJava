package com.meti.interpret;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum PrimitiveType implements Type {
	INT("int"),
    STRING("string"), BOOLEAN("boolean");

	private final String value;

	//TODO: add more types
	PrimitiveType(String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public List<Type> parameters() {
		return new ArrayList<>();
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}
}
