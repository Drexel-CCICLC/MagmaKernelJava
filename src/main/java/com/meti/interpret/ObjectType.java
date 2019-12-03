package com.meti.interpret;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ObjectType implements Type {
    private final String name;

    ObjectType(String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return name;
    }

    @Override
    public List<Type> parameters() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Type> returnType() {
        return Optional.empty();
    }
}
