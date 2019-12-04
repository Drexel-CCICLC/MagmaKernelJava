package com.meti.lex;

import java.util.Optional;

class EndTokenizer implements Tokenizer {
    @Override
    public Optional<? extends Token<?>> apply(LexerState lexerState) {
        String value = lexerState.compute();
        if (value.equals(";")) {
            return Optional.of(new TokenImpl<>(TokenType.END, null));
        } else {
            return Optional.empty();
        }
    }
}
