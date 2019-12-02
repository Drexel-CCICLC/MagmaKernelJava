package com.meti;

import java.util.Optional;

public interface LexerState {
	Optional<Token<?>> next(Optional<Token<?>> optional);

	String compute();

	boolean hasMoreTokens();
}
