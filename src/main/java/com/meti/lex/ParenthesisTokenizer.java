package com.meti.lex;

import java.util.Optional;

public class ParenthesisTokenizer implements Tokenizer {
	@Override
	public Optional<? extends Token<?>> apply(LexerState lexerState) {
		String compute = lexerState.compute();
		if (compute.equals("(") || compute.equals(")")) {
			return Optional.of(new TokenImpl<>(TokenType.PARENTHESIS, compute.equals("(")));
		} else {
			return Optional.empty();
		}
	}
}
