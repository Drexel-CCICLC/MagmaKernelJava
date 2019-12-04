package com.meti.lex;

import java.util.Optional;

abstract class FunctionalTokenizer implements Tokenizer {
    @Override
    public Optional<? extends Token<?>> apply(LexerState lexerState) {
        return validate(lexerState) ?
                Optional.of(buildToken(lexerState)) :
                Optional.empty();
    }

    protected abstract Token<?> buildToken(LexerState lexerState);

    protected abstract boolean validate(LexerState lexerState);
}
