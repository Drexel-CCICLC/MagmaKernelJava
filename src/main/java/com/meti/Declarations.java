package com.meti;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;

interface Declarations {
    Declaration absolute(Collection<String> names);

    void defineSibling(String name, Type type, StringBuilder callback);

    Declaration parent();

    String pop();

    void push(String name);

    Optional<Declaration> relative(String name);

    Declaration root();

    Stack<String> stack();
}
