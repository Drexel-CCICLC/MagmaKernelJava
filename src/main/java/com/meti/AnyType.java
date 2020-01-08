package com.meti;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AnyType implements Type {
	public static final Type INSTANCE = new AnyType();

	@Override
	public Optional<Type> child() {
		return Optional.empty();
	}

	@Override
	public boolean isPointer() {
		return render().endsWith("*");
	}

	@Override
	public List<Type> parameters() {
		return Collections.emptyList();
	}

	@Override
	public String render() {
		return "void*";
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}
}
