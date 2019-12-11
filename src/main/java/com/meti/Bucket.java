package com.meti;

import java.util.function.Predicate;

public interface Bucket {
	static Bucket build() {
		return new BucketImpl();
	}

	String collect();

	Bucket exclude(char c);

	Bucket excludeWhitespace();

	Bucket pour(String value);

	Bucket restrict(int count);

	final class
	BucketImpl implements Bucket {
		private final StringBuilder builder = new StringBuilder();
		private Predicate<Character> predicate = character -> true;

		private BucketImpl() {
		}

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
		public Bucket pour(String value) {
			for (char c : value.toCharArray()) {
				pour(c);
			}
			return this;
		}

		private void pour(char value) {
			if (predicate.test(value)) builder.append(value);
		}

		@Override
		public Bucket restrict(int count) {
			predicate = predicate.and(character -> builder.length() < count);
			return this;
		}
	}
}
