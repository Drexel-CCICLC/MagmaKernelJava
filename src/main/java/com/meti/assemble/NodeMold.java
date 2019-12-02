package com.meti.assemble;

import com.meti.lex.Token;

import java.util.List;
import java.util.Optional;

interface NodeMold {
    default void pourAll(List<Token<?>> tokens) {
        tokens.forEach(this::pour);
    }

    void pour(Token<?> token);

    Optional<Node> set();
}
