package com.meti.assemble;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class PredicateBucketTest {

	@Test
	void append() {
		List<String> numbers = new PredicateBucket<>(PredicateBucketTest::isNumber)
				.appendAll("123", "abvis", "1038")
				.stream()
				.collect(Collectors.toList());
		assertIterableEquals(List.of("123", "1038"), numbers);
	}

	private static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Test
	void stream() {
		Bucket<String> bucket = new PredicateBucket<>(string -> true);
		assertEquals(0L, bucket.stream().count());
	}
}