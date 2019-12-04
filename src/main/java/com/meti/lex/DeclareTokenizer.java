package com.meti.lex;

import java.util.Optional;

class DeclareTokenizer extends FunctionalTokenizer {
    @Override
    protected Token<?> buildToken(LexerState lexerState) {
        return new TokenImpl<>(TokenType.DECLARE, lexerState.compute().equals("var"));
    }

    @Override
    protected boolean validate(LexerState lexerState) {
        Optional<Character> trailing = lexerState.trailing();
        String computed = lexerState.compute();
        boolean isValid = computed.equals("var") ||
                computed.equals("val");
        return isValid && (trailing.isEmpty() || trailing.get() == ' ');
    }
}
