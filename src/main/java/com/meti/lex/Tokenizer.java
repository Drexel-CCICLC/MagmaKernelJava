package com.meti.lex;

import java.util.Optional;
import java.util.function.Function;

public interface Tokenizer extends Function<LexerState, Optional<? extends Token<?>>> {
}
