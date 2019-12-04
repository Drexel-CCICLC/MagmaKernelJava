package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.Optional;
import java.util.function.Predicate;

class DeclareMold implements NodeMold {
    private final BucketManager<Token<?>> manager = new ListBucketManager<Token<?>>(
            by(type(TokenType.DECLARE).and(single())),
            by(type(TokenType.CONTENT).and(single())),
            by(type(TokenType.CONTENT).and(single()).and(valueEquals("="))),
            by(type(TokenType.END).negate()),
            by(type(TokenType.END)));

    private static Predicate<Token<?>> type(final TokenType type) {
        return token -> token.type().equals(type);
    }

    private Predicate<Token<?>> valueEquals(final Object value) {
        return token -> token.value().equals(value);
    }

    private Predicate<? super Token<?>> single() {
        return new Predicate<>() {
            private int counter = -1;

            @Override
            public boolean test(Token<?> token) {
                counter++;
                return counter < 1;
            }
        };
    }

    private PredicateBucket by(Predicate<Token<?>> predicate) {
        return new PredicateBucket(predicate);
    }

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
