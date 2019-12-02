package com.meti.lex;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class StringLexerState implements LexerState {
    private final String value;
    private int beginning = 0;
    private int end = 1;

    public StringLexerState(String value) {
        this.value = value;
    }

    @Override
    public Optional<Token<?>> next(Set<? extends Function<LexerState, Optional<Token<?>>>> functions) {
        var optional = functions.stream()
                .map(state -> state.apply(this))
                .flatMap(Optional::stream)
                .findAny();
        if(optional.isPresent()) advance();
        else extend();
        return optional;
    }

    private void advance() {
        beginning = end;
        end = beginning + 1;
    }

    private void extend() {
        end++;
    }

    @Override
    public String compute() {
        return value.substring(beginning, end);
    }

    @Override
    public boolean hasMoreTokens() {
        return beginning != value.length();
    }
}
