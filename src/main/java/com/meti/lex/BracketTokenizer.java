package com.meti.lex;

import java.util.Optional;

public class BracketTokenizer implements Tokenizer {
	@Override
	public Optional<? extends Token<?>> apply(LexerState lexerState) {
		String compute = lexerState.compute();
		if (compute.equals("{") || compute.equals("}")) {
			return Optional.of(new TokenImpl<>(TokenType.BRACKET, compute.equals("{")));
		} else {
			return Optional.empty();
		}
	}
}
