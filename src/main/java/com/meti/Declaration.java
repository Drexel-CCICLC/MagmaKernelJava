package com.meti;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Declaration {
    private final String name;
    private final Type type;
    private final Map<String, Declaration> children = new HashMap<>();

    public Declaration(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Optional<Declaration> child(String name) {
        return Optional.ofNullable(children.get(name));
    }

    public String name() {
        return name;
    }

    public void define(String name, Type type) {
        children.put(name, new Declaration(name, type));
    }
}
