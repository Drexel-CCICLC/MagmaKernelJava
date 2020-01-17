package com.meti.node.value.primitive.array;

import com.meti.node.Type;
import com.meti.node.other.VoidType;

import java.util.Optional;
import java.util.OptionalInt;

public class ArrayType implements Type {
    private final Type elementType;

    public ArrayType(Type elementType) {
        this.elementType = elementType;
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
	public String renderWithName(String name) {
		return (isNamed()) ? render() : render() + " " + name;
	}

	@Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }

    @Override
    public boolean isNamed() {
        return false;
    }

    @Override
    public String render() {
        return elementType.render() + "*";
    }

	@Override
	public boolean doesReturnVoid() {
		return returnType().isPresent() && returnType().get() instanceof VoidType;
	}
}
