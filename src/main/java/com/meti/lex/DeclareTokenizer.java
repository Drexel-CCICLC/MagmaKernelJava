package com.meti.lex;

import java.util.Optional;

class DeclareTokenizer implements Tokenizer {
    @Override
    public Optional<? extends Token<?>> apply(LexerState lexerState) {
        return Optional.of(new TokenImpl<>(TokenType.DECLARE, lexerState.compute().equals("var")))
                .filter((token -> hasValidTrail(lexerState)));
    }

    private boolean hasValidTrail(LexerState lexerState) {
        Optional<Character> trailing = lexerState.trailing();
        return trailing.isEmpty() || trailing.get() == ' ';
    }
}
