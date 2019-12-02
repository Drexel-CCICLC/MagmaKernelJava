package com.meti;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface LexerState {
    Optional<Token<?>> next(Set<? extends Function<LexerState, Optional<Token<?>>>> functions);

    String compute();

	boolean hasMoreTokens();
}
