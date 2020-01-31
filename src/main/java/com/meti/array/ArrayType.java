package com.meti.array;

import com.meti.Type;

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
    public String render(String name) {
        return render() + " " + name;
    }
}
