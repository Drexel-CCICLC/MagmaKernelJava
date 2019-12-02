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
        return trailing.isEmpty() || trailing.get() == ' ';
    }
}
