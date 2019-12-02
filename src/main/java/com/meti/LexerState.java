package com.meti;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface LexerState {
    Optional<Token<?>> next(Set<Function<LexerState, Optional<Token<?>>>> functions);

    Optional<Token<?>> next(Optional<Token<?>> optional);

	String compute();

	boolean hasMoreTokens();
}
