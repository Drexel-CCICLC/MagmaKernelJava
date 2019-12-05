package com.meti.assemble;

import com.meti.lex.Token;

import java.util.Optional;
import java.util.stream.Stream;

interface Mold {
    default void pourAll(Stream<? extends Token<?>> tokens) {
        tokens.forEach(this::pour);
    }

    void pour(Token<?> token);

    Optional<Node> set(Assembler assembler);
}
