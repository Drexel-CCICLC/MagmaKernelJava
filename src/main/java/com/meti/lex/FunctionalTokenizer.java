package com.meti.lex;

import java.util.Optional;

abstract class FunctionalTokenizer implements Tokenizer {
    @Override
    public Optional<? extends Token<?>> apply(LexerState lexerState) {
        return Optional.of(buildToken(lexerState))
                .filter((token -> validate(lexerState)));
    }

    protected abstract Token<?> buildToken(LexerState lexerState);

    protected abstract boolean validate(LexerState lexerState);
}
