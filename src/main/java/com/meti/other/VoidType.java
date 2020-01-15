package com.meti.other;

import com.meti.Type;

import java.util.Optional;

public class VoidType implements Type {
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
}
