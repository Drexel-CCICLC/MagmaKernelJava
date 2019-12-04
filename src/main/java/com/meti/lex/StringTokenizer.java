package com.meti.lex;

class StringTokenizer extends FunctionalTokenizer {
    @Override
    protected Token<?> buildToken(LexerState lexerState) {
        String value = lexerState.compute();
        return new TokenImpl<>(TokenType.STRING, value.substring(1, value.length() - 1));
    }

    @Override
    protected boolean validate(LexerState lexerState) {
        String value = lexerState.compute();
        if(value.equals("\"")) return false;
        return value.startsWith("\"") &&
                value.endsWith("\"");
    }
}
