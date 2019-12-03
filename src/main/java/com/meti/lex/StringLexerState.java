package com.meti.lex;

import java.util.Optional;
import java.util.Set;

public class StringLexerState implements LexerState {
    private final String value;
    private int beginning = 0;
    private int end = 1;

    public StringLexerState(String value) {
        this.value = value;
    }

    @Override
    public Optional<? extends Token<?>> next(Set<? extends Tokenizer> functions) {
        var optional = functions.stream()
                .map(state -> state.apply(this))
                .flatMap(Optional::stream)
                .findAny();
        if (optional.isPresent()) advance();
        else extend();
        return optional;
    }

    @Override
    public Optional<Character> trailing() {
        if (hasMoreCharacters()) return Optional.of(value.charAt(end));
        else return Optional.empty();
    }

    private void advance() {
        beginning = end;
        end = beginning + 1;
    }

    @Override
    public LexerState extend() {
        end++;
        return this;
    }

    @Override
    public String compute() {
        return value.substring(beginning, end);
    }

    @Override
    public boolean hasMoreCharacters() {
        return end != value.length() - 1;
    }
}
