package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.Optional;
import java.util.function.Predicate;

class DeclareMold implements NodeMold {
    private final PredicateBucket declare = by(type(TokenType.DECLARE).and(count(1)));
    private final PredicateBucket name = by(type(TokenType.CONTENT).and(count(1)));
    private final PredicateBucket content = by(type(TokenType.END).negate());
    private final PredicateBucket end = by(type(TokenType.END));
    private final BucketManager<Token<?>> manager = new ListBucketManager<Token<?>>(
            declare, name, content, end);

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
        //manager.allPresent()?
        boolean mutable = manager.atSingle(0).castedValue();
        String name = manager.atSingle(1).castedValue();
        Node content = assembler.assemble(manager.at(2))
                .findAny()
                .orElseThrow();
        return Optional.of(new DeclareNode(mutable, name, content));
    }
}
