package com.meti;

import java.util.function.Predicate;

public class Bucket {
	private final StringBuilder builder = new StringBuilder();
	private Predicate<Character> predicate = character -> true;

	public String content() {
		return builder.toString();
	}

	public Bucket excludeWhitespace() {
		return exclude(' ')
				.exclude('\t')
				.exclude('\n');
	}

	public Bucket exclude(char c) {
		predicate = predicate.and(((Predicate<Character>) character -> character == c).negate());
		return this;
	}

	public Bucket pour(String value) {
		for (char c : value.toCharArray()) {
			pour(c);
		}
		return this;
	}

	private void pour(char value) {
		if (predicate.test(value)) builder.append(value);
	}

	public Bucket truncate(int count) {
		predicate = predicate.and(character -> builder.length() < count);
		return this;
	}
}
