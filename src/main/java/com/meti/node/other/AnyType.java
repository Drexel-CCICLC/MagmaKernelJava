package com.meti.node.other;

import com.meti.node.Type;

import java.util.Optional;
import java.util.OptionalInt;

public class AnyType implements Type {
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