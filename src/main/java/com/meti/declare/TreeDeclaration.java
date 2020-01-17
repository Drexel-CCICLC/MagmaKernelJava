package com.meti.declare;

import com.meti.node.Type;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class TreeDeclaration implements Declaration {
    private final String name;
    private final Declaration parent;
    private final Type type;
    private final Collection<Declaration> children = new HashSet<>();

    protected TreeDeclaration(String name, Declaration parent, Type type) {
        this.name = name;
        this.parent = parent;
        this.type = type;
    }

    @Override
    public Map<String, Type> childMap() {
        return children.stream()
                .collect(Collectors.toMap(Declaration::name, Declaration::type));
    }

    @Override
    public Declaration define(String name, DeclarationBuilder builder) {
        Declaration declaration = builder.build(this);
        children.add(declaration);
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
        return children.stream()
                .filter(declaration -> declaration.isNamedAs(name))
                .findFirst();
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
