package com.meti.declare;

import com.meti.node.Node;
import com.meti.node.Type;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Declarations {
    Declaration current();

    Node define(String name, Supplier<? extends Node> action, DeclarationBuilder builder);

    void define(String name, DeclarationBuilder builder);

    Declaration root();

    Optional<Declaration> relative(String value);

    Stream<Declaration> stream();

    void define(String name, Type type, Runnable action);

    default boolean isCurrent(Declaration obj) {
        return current().equals(obj);
    }

    default boolean isRoot(Declaration obj) {
        return root().equals(obj);
    }
}
