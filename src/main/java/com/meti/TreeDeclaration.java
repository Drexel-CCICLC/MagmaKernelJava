package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.meti.Flag.NATIVE;

public class TreeDeclaration implements Declaration {
    private final StringBuilder callback = new StringBuilder();
    private final List<Declaration> children = new ArrayList<>();
    private final String name;
    private final Type type;
    private final List<Flag> flags;

    public TreeDeclaration(List<Flag> flags, Type type, String name) {
        this.name = name;
        this.type = type;
        this.flags = flags;
    }

    @Override
    public StringBuilder callback() {
        return callback;
    }

    @Override
    public Optional<Declaration> child(String name) {
        return children.stream()
                .filter(declaration -> declaration.matches(name))
                .findAny();
    }

    @Override
    public Declaration define(DeclarationBuilder builder) {
        Declaration child = builder.build();
        children.add(child);
        return child;
    }

    @Override
    public Type typeOf() {
        return type;
    }

    @Override
    public boolean isMain() {
        return name.equals("main");
    }

    @Override
    public boolean isNative() {
        return flags.contains(NATIVE);
    }

    private String renderWith(String value) {
        return type.render() + " "
                + name + " "
                + value + ";";
    }

    @Override
    public String render() {
        return name;
    }

    @Override
    public boolean matches(String name) {
        return this.name.equals(name);
    }

    private boolean shouldRender() {
        return !isMain() && !isNative();
    }

    private Optional<String> render(String value) {
        return Optional.of(value)
                .filter(other -> shouldRender())
                .map(this::renderWith);
    }

    @Override
    public String render(Compiler compiler, String value) {
        String compiledValue = compiler.compileOnly(value);
        render(compiledValue).ifPresent(callback::append);
        return callback.toString();
    }
}
