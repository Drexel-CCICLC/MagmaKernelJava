package com.meti.interpret;

import com.meti.assemble.Node;

import java.util.Optional;

interface Resolver {
    Optional<Type> resolve(Node node, Interpreter interpreter);

    Optional<? extends Type> resolve(String value);
}
