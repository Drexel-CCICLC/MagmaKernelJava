package com.meti.lex;

import java.util.Optional;
import java.util.Set;

public interface LexerState {
    Optional<? extends Token<?>> next(Set<? extends Tokenizer> functions);

    Optional<Character> trailing();

    Optional<String> trailing(int count);

    void advance();

    void skipWhitespace();

    LexerState extend();

    String compute();

    boolean hasMoreToScan();
}