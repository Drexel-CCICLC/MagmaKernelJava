package com.meti.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.List.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class PredicateStreamSplitterTest {

	@Test
	void split() {
		List<List<Integer>> lists = new PredicateStreamSplitter<Integer>(integer -> integer % 2 == 0)
				.split(IntStream.of(1, 5, 8, 3, 9).boxed())
				.map(integerStream -> integerStream.collect(Collectors.toList()))
				.collect(Collectors.toList());
		assertIterableEquals(of(1, 5), lists.get(0));
		assertIterableEquals(of(3, 9), lists.get(1));
	}
}