package com.meti.lex;

public interface Token<T> {
	TokenType type();

	T value();
}
