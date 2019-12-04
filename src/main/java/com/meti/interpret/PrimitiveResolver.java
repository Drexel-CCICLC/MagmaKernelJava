package com.meti.interpret;

import com.meti.assemble.IntNode;
import com.meti.assemble.Node;

import java.util.Arrays;
import java.util.Optional;

class PrimitiveResolver implements Resolver {
    @Override
    public Optional<Type> resolve(Node node) {
        if (node instanceof IntNode) {
            return Optional.of(PrimitiveType.INT);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<? extends Type> resolve(String value) {
        return Arrays.stream(PrimitiveType.values())
                .filter(primitiveType -> primitiveType.value().equals(value))
                .findAny();
    }
}
