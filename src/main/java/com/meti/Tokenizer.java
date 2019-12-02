package com.meti;

import java.util.Optional;

public interface Tokenizer {
	Optional<Token<?>> tokenize(LexerState state);
}
