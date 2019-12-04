package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.Optional;
import java.util.function.Predicate;

class AssignMold extends SimpleMold {
    private final BucketManager<Token<?>> manager = new ListBucketManager<Token<?>>(
            by(type(TokenType.CONTENT).and(single())),
            by(type(TokenType.CONTENT).and(single()).and(valueEquals("="))),
            by(type(TokenType.END).negate()),
            by(type(TokenType.END)));

    @Override
    public void pour(Token<?> token) {
        manager.put(token);
    }

    @Override
    public Optional<Node> set(Assembler assembler) {
        if (manager.allPresent()) {
            String name = manager.atSingle(0).castedValue();
            Node content = assembler.assemble(manager.at(2))
                    .findAny()
                    .orElseThrow();
            return Optional.of(new AssignNode(name, content));
        } else {
            return Optional.empty();
        }
    }
}
