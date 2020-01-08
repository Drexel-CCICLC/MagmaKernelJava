package com.meti;

import java.util.Optional;

public class AnyType implements PointerType {
	public static final Type INSTANCE = new AnyType();

	@Override
	public Optional<Type> child() {
		return Optional.empty();
	}

	@Override
	public boolean isVariable() {
		return render().equals(".");
	}

	@Override
	public String render() {
		return "void*";
	}
}
