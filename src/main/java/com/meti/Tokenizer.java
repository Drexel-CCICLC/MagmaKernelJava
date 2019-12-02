package com.meti;

import java.util.Optional;
import java.util.function.Function;

public interface Tokenizer extends Function<LexerState, Optional<Token<?>>> {
}
