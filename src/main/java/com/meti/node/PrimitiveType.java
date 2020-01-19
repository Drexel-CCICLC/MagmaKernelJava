package com.meti.node;

import com.meti.node.other.VoidType;

import java.util.Optional;
import java.util.OptionalInt;

public abstract class PrimitiveType implements Type {
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
		return false;
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
