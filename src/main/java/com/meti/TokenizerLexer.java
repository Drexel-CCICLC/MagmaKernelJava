package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TokenizerLexer implements Lexer {
	private final Set<? extends Tokenizer> tokenizers;

	public TokenizerLexer(Set<? extends Tokenizer> tokenizers) {
		this.tokenizers = tokenizers;
	}

	@Override
	public List<Token<?>> parse(String value) {
		LexerState state = new StringLexerState(value);
		List<Token<?>> tokens = new ArrayList<>();
		while (state.hasMoreTokens()) {
			Optional<Token<?>> optional = parse(state);
			state.next(optional).ifPresent(tokens::add);
		}
		return tokens;
	}

	private Optional<Token<?>> parse(LexerState state) {
		return tokenizers.stream()
				.map(tokenizer -> tokenizer.tokenize(state))
				.flatMap(Optional::stream)
				.findAny();
	}
}
