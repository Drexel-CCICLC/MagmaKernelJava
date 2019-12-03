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
    public String toString() {
        return "StringLexerState{" +
                "value='" + compute() + '\'' +
                '}';
    }

    @Override
    public Optional<Character> trailing() {
        return trailing(1).map(value -> value.charAt(0));
    }

    @Override
    public Optional<String> trailing(int count) {
        if (end < value.length()) {
            var endIndex = Math.min(value.length(), end + count);
            return Optional.of(value.substring(end, endIndex));
        } else return Optional.empty();
    }

    @Override
    public void advance() {
        beginning = end;
        end = beginning + 1;
    }

    @Override
    public void skipWhitespace() {
        while (compute().charAt(0) == ' ') {
            advance();
        }
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
    public boolean hasMoreToScan() {
        return beginning < value.length();
    }
}
