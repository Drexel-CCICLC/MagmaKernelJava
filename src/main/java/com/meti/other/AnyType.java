package com.meti.other;

import com.meti.Type;

import java.util.Optional;

public class AnyType implements Type {
    @Override
    public boolean isNamed() {
        throw new UnsupportedOperationException("AnyType cannot be named.");
    }

    @Override
    public String render() {
        return "...";
    }

    @Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }
}
