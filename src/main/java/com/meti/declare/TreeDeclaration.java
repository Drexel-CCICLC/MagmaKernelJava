package com.meti.declare;

import com.meti.node.Type;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class TreeDeclaration implements Declaration {
    protected final String name;
    protected final Declaration parent;
    protected final Type type;
    private final Map<String, Declaration> children = new LinkedHashMap<>();

    public TreeDeclaration(String name, Declaration parent, Type type) {
        this.name = name;
        this.parent = parent;
        this.type = type;
    }

    @Override
    public Map<String, Type> childMap() {
        return children.keySet()
                .stream()
                .collect(Collectors.toMap(Function.identity(),
                        ((Function<String, Declaration>) children::get).andThen(Declaration::type)));
    }

    @Override
    public Declaration define(String name, DeclarationBuilder builder) {
        Declaration declaration = builder.build(this);
        children.put(name, declaration);
        return declaration;
    }

    @Override
    public boolean hasParameter(String childName) {
        return child(childName)
                .map(Declaration::isParameter)
                .orElse(false);
    }

    @Override
    public Optional<Declaration> child(String name) {
        return Optional.ofNullable(children.get(name));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Optional<Declaration> parent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public Type type() {
        return type;
    }
}
