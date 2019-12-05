package com.meti.lex;

import java.util.Optional;

class EndTokenizer implements Tokenizer {
	private final Binding<Integer> depth;

	public EndTokenizer(Binding<Integer> depth) {
		this.depth = depth;
	}

	@Override
	public Optional<? extends Token<?>> apply(LexerState lexerState) {
		String value = lexerState.compute();
		if (value.equals(";")) {
            return Optional.of(new TokenImpl<>(TokenType.END, depth.get()));
		} else {
			return Optional.empty();
		}
	}
}
