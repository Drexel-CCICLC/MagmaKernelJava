package com.meti.node.other;

import com.meti.node.Type;

import java.util.Optional;
import java.util.OptionalInt;

public class VoidType implements Type {
	public static final Type INSTANCE = new VoidType();

	public static Type INSTANCE() {
		return INSTANCE;
	}

	@Override
	public OptionalInt childOrder(String childName) {
		return OptionalInt.empty();
	}

	@Override
	public Optional<Type> childType(String childName) {
		return Optional.empty();
	}

	@Override
	public boolean isNamed() {
		return false;
	}

	@Override
	public String render() {
		return "void";
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
