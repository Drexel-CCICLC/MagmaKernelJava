package com.meti;

import java.util.Optional;

public class AnyType implements ParentType {
	public static final Type INSTANCE = new AnyType();

	@Override
	public Optional<Type> child() {
		return Optional.empty();
	}

	@Override
	public String render() {
		return "void*";
	}
}
