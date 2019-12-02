package com.meti;

public interface Token<T> {
	TokenType type();

	T value();
}
