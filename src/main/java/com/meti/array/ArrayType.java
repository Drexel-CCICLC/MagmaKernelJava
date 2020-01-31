package com.meti.array;

import com.meti.Type;

import java.util.Optional;
import java.util.OptionalInt;

public abstract class ArrayType implements Type {
    protected final Type elementType;

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
}
