package com.meti.array;

import com.meti.Type;

import java.util.Optional;

public class ArrayType implements Type {
    private final Type elementType;

    public ArrayType(Type elementType) {
        this.elementType = elementType;
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
}
