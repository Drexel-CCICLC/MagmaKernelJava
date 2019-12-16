package com.meti;

import java.util.function.Predicate;

public interface Bucket {
	static Bucket build() {
		return new BucketImpl();
	}

	String collect();

	Bucket exclude(char c);

	Bucket excludeWhitespace();

	Bucket include(char c);

	Bucket pour(String value);

	boolean pour(char value);

	Bucket restrict(int count);

	final class BucketImpl implements Bucket {
		private final StringBuilder builder = new StringBuilder();
		private boolean isAlwaysTrue = true;
		private Predicate<Character> predicate = character -> true;

		@Override
		public String collect() {
			return builder.toString();
		}

		@Override
		public Bucket exclude(char c) {
			predicate = predicate.and(((Predicate<Character>) character -> character == c).negate());
			return this;
		}

		@Override
		public Bucket excludeWhitespace() {
			return exclude(' ')
					.exclude('\t')
					.exclude('\n');
		}

		@Override
		public Bucket include(char c) {
			Predicate<Character> isChar = character -> character == c;
			predicate = isAlwaysTrue ? isChar : predicate.or(isChar);
			if(isAlwaysTrue) isAlwaysTrue = false;
			return this;
		}

		@Override
		public Bucket pour(String value) {
			for (char c : value.toCharArray()) {
				pour(c);
			}
			return this;
		}

		@Override
		public boolean pour(char value) {
			if (predicate.test(value)) {
				builder.append(value);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Bucket restrict(int count) {
			predicate = predicate.and(character -> builder.length() < count);
			return this;
		}
	}
}
