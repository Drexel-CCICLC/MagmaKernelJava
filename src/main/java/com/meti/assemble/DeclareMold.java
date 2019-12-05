package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.Optional;

class DeclareMold extends SimpleMold {
    private final BucketManager<Token<?>> manager = new ListBucketManager<Token<?>>(
            by(type(TokenType.DECLARE).and(single())),
            by(type(TokenType.CONTENT).and(single())),
            by(type(TokenType.CONTENT).and(single()).and(valueEquals("="))),
            by(any()));

    @Override
    public void pour(Token<?> token) {
        manager.put(token);
    }

    @Override
    public Optional<Node> set(Assembler assembler) {
        if (manager.allPresent()) {
            boolean mutable = manager.atSingle(0).castedValue();
            String name = manager.atSingle(1).castedValue();
            Node content = assembler.assemble(manager.at(3))
                    .findAny()
                    .orElseThrow();
            return Optional.of(new DeclareNode(mutable, name, content));
        } else {
            return Optional.empty();
        }
    }
}
