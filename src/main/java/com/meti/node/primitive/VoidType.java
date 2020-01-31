package com.meti.node.primitive;

import com.meti.node.Type;

import java.util.Optional;
import java.util.OptionalInt;

public class VoidType implements Type {
	public static final Type INSTANCE = new VoidType();

	@Override
	public OptionalInt childOrder(String name) {
		return null;
	}

	@Override
	public Optional<Type> childType(String name) {
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
	public Optional<Type> returnType() {
		return Optional.empty();
	}

	@Override
	public String render(String name) {
		return null;
	}
}