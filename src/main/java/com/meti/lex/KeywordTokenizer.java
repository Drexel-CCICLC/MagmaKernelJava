package com.meti.lex;

import java.util.Optional;

public class KeywordTokenizer implements Tokenizer {
	@Override
	public Optional<? extends Token<?>> apply(LexerState lexerState) {
		String compute = lexerState.compute();
		String keyword = compute.toUpperCase();
		try {
			Keyword value = Keyword.valueOf(keyword);
			return Optional.of(new TokenImpl<>(TokenType.KEYWORD, value));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}
}
