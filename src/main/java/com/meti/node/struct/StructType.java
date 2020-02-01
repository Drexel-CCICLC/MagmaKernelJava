package com.meti.node.struct;

import com.meti.node.Type;

import java.util.Optional;
import java.util.OptionalInt;

public class StructType implements Type {
	private final String name;

	public StructType(String name) {
		this.name = name;
	}

	@Override
	public OptionalInt childOrder(String name) {
		return OptionalInt.empty();
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
		return "struct " + name;
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}

	@Override
	public String render(String name) {
		return "struct " + this.name + " " + name;
	}
}
