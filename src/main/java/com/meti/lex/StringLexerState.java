package com.meti.lex;

import java.util.Optional;
import java.util.Set;

public class StringLexerState implements LexerState {
	private final Set<Character> skippedCharacters = Set.of(
			' ',
			'\n',
			'\t'
	);
	private final String value;
	private int beginning = 0;
	private int end = 1;

	public StringLexerState(String value) {
		this.value = value;
	}

	@Override
	public Optional<? extends Token<?>> next(Set<? extends Tokenizer> functions) {
		var optional = functions.stream()
				.map(state -> state.apply(this))
				.flatMap(Optional::stream)
				.findAny();
		if (optional.isPresent()) advance();
		else extend();
		return optional;
	}

	@Override
	public Optional<Character> trailing() {
		if (end < value.length()) {
			var endIndex = Math.min(value.length(), end + 1);
			return Optional.of(value.substring(end, endIndex))
					.map(string -> string.charAt(0));
		} else return Optional.empty();
	}

	@Override
	public void advance() {
		beginning = end;
		end = beginning + 1;
	}

	@Override
	public void skip() {
		String value = compute();
		if(!value.isEmpty() && skippedCharacters.contains(value.charAt(0))){
			advance();
			skip();
		}
	}

	@Override
	public LexerState extend() {
		end++;
		return this;
	}

	@Override
	public String compute() {
		try {
			return value.substring(beginning, end);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public boolean hasMoreToScan() {
		return beginning < value.length();
	}

	@Override
	public String toString() {
		return "StringLexerState{" +
				"value='" + compute() + '\'' +
				'}';
	}
}
