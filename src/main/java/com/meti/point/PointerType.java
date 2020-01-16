package com.meti.point;

import com.meti.Type;
import com.meti.other.AnyType;

import java.util.Optional;
import java.util.OptionalInt;

public class PointerType implements Type {
	private final Type child;

	public PointerType(Type child) {
		this.child = child;
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
	public Optional<Type> returnType() {
		return Optional.empty();
	}

	@Override
	public boolean isNamed() {
		return child.isNamed();
	}

	@Override
	public String render() {
		//TODO: rule changes with functions?
		if (child instanceof AnyType) {
			return "void*";
		} else {
			return child.render() + "*";
		}
	}
}
