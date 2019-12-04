package com.meti.lex;

import java.util.Optional;

class IntegerTokenizer implements Tokenizer {
    @Override
    public Optional<? extends Token<?>> apply(LexerState lexerState) {
        String value = lexerState.compute();
        Optional<Character> trailing = lexerState.trailing();
        if (trailing.isPresent() && Character.isDigit(trailing.get())) return Optional.empty();
        try {
            return Optional.of(new TokenImpl<>(TokenType.INT, Integer.parseInt(value)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
