package com.meti;

import java.util.Optional;

public class StringLexerState implements LexerState {
	private final String value;
	private int beginning = 0;
	private int end = 1;

	public StringLexerState(String value) {
		this.value = value;
	}

	@Override
	public Optional<Token<?>> next(Optional<Token<?>> optional) {
		if (optional.isPresent()) advance();
		else extend();
		return optional;
	}

	private void advance() {
		beginning = end;
		end = beginning + 1;
	}

	private void extend() {
		end++;
	}

	@Override
	public String compute() {
		return value.substring(beginning, end);
	}

	@Override
	public boolean hasMoreTokens() {
		return beginning != value.length();
	}
}
