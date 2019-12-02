package com.meti.lex;

class TokenImpl<T> implements Token<T> {
    private final TokenType type;
    private final T value;

    TokenImpl(TokenType type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public TokenType type() {
        return type;
    }

    @Override
    public T value() {
        return value;
    }
}
