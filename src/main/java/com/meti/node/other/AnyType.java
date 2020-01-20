package com.meti.node.other;

import com.meti.node.Type;

import java.util.Optional;
import java.util.OptionalInt;

public class AnyType implements Type {
	public static final Type INSTANCE = new AnyType();

	public static Type INSTANCE() {
		return INSTANCE;
	}

	@Override
	public OptionalInt childOrder(String childName) {
		return OptionalInt.empty();
	}

	@Override
	public Optional<Type> childType(String childType) {
		return Optional.empty();
	}

	@Override
	public boolean isNamed() {
		throw new UnsupportedOperationException("AnyType cannot be named.");
	}

	@Override
	public String render() {
		return "...";
	}

	@Override
	public String renderWithName(String name) {
		return (isNamed()) ? render() : render() + " " + name;
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}

	@Override
	public boolean doesReturnVoid() {
		return returnType().isPresent() && returnType().get() instanceof VoidType;
	}
}
