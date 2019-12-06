package com.meti.lex;

import java.util.Optional;

public class BracketTokenizer implements Tokenizer {
	private final Binding<Integer> depth;

	public BracketTokenizer(Binding<Integer> depth) {
		this.depth = depth;
	}

	@Override
	public Optional<? extends Token<?>> apply(LexerState lexerState) {
		String compute = lexerState.compute();
		if (compute.equals("{") || compute.equals("}")) {
			boolean opened = compute.equals("{");
			if(opened) {
				depth.set(depth.get() + 1);
			} else {
				depth.set(depth.get() - 1);
			}
			return Optional.of(new TokenImpl<>(TokenType.BRACKET, opened));
		} else {
			return Optional.empty();
		}
	}
}
