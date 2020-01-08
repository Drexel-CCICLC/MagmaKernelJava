package com.meti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FunctionalPartitioner implements Partitioner {
	private final Function<Character, Optional<Character>> mapper;
	private final Collection<String> partitions = new ArrayList<>();
	private final Predicate<Character> splicer;
	private StringBuilder builder = new StringBuilder();

	private FunctionalPartitioner(Predicate<Character> splicer, Function<Character, Optional<Character>> mapper) {
		this.splicer = splicer;
		this.mapper = mapper;
	}

	public static FunctionalPartitioner using(Predicate<Character> splicer, Function<Character,
			Optional<Character>> mapper) {
		return new FunctionalPartitioner(splicer, mapper);
	}

	@Override
	public Stream<String> partition(String value) {
		partitions.clear();
		char[] charArray = value.toCharArray();
		for (char c : charArray) {
			partitionCharacter(c);
		}
		completePartition();
		return partitions.stream()
				.filter(p -> !p.isBlank());
	}

	private void partitionCharacter(char c) {
		if (splicer.test(c)) {
			completePartition();
			builder = new StringBuilder();
		} else builder.append(mapper.apply(c));
	}

	private void completePartition() {
		String partition = builder.toString();
		partitions.add(partition);
	}
}