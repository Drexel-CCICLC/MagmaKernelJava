package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

class DeclareMold implements NodeMold {
    private final BucketManager<Token<?>> manager = new ListBucketManager(
            by(type(TokenType.DECLARE).and(count(1))),
            by(type(TokenType.CONTENT).and(count(1))),
            by(type(TokenType.END).negate()),
            by(type(TokenType.END)));

    private static Predicate<Token<?>> type(final TokenType type) {
        return token -> token.type().equals(type);
    }

    private Predicate<? super Token<?>> count(int count) {
        return new Predicate<>() {
            private int counter = -1;

            @Override
            public boolean test(Token<?> token) {
                counter++;
                return counter < count;
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
        boolean mutable = manager.atSingle(0).castedValue();
        String name = manager.atSingle(1).castedValue();
        Stream<Node> content = assembler.assemble(manager.at(2));
        return Optional.of(new DeclareNode(mutable, name, content));
    }
}
