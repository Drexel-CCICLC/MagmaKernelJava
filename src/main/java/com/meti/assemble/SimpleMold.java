package com.meti.assemble;

import com.meti.lex.Token;
import com.meti.lex.TokenType;

import java.util.function.Predicate;

abstract class SimpleMold implements NodeMold {
	protected static Predicate<Token<?>> any() {
		return token -> true;
    }

	protected static Predicate<Token<?>> type(final TokenType type) {
        return token -> token.type().equals(type);
    }

    protected static Predicate<Token<?>> valueEquals(final Object value) {
        return token -> token.value().equals(value);
    }

    protected static Predicate<? super Token<?>> single() {
        return new Predicate<>() {
            private int counter = -1;

            @Override
            public boolean test(Token<?> token) {
                counter++;
                return counter < 1;
            }
        };
    }

    protected static Bucket by(Predicate<Token<?>> predicate) {
        return new PredicateBucket(predicate);
    }
}
