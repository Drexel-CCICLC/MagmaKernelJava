package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Bucket {
	private final List<Character> content = new ArrayList<>();
	private Predicate<Character> predicate = character -> true;

	public List<Character> content() {
		return content;
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
		if (predicate.test(value)) content.add(value);
	}

	public Bucket truncate(int count) {
		predicate = predicate.and(character -> content.size() < count);
		return this;
	}
}
