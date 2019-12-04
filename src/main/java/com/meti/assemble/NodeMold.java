package com.meti.assemble;

import com.meti.lex.Token;

import java.util.Optional;
import java.util.stream.Stream;

interface NodeMold {
    default void pourAll(Stream<Token<?>> tokens) {
        tokens.forEach(this::pour);
    }

    void pour(Token<?> token);

    Optional<Node> set(Assembler assembler);
}
