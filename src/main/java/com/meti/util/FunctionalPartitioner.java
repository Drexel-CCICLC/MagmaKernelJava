package com.meti.util;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

public class FunctionalPartitioner implements Partitioner {
	private final Function<Character, Optional<Character>> mapper;
	private final Predicate<Character> splicer;
	private StringBuilder concatenator = new StringBuilder();

	private FunctionalPartitioner(Predicate<Character> splicer, Function<Character, Optional<Character>> mapper) {
		this.splicer = splicer;
		this.mapper = mapper;
	}

	public static Partitioner using(Predicate<Character> splicer, Function<Character,
			Optional<Character>> mapper) {
		return new FunctionalPartitioner(splicer, mapper);
	}

	@Override
	public Stream<String> partition(String value) {
		return concat(of(value.toCharArray())
                        .map(ArrayUtils::toObject)
						.flatMap(Arrays::stream)
						.map(this::partitionCharacter)
						.flatMap(Optional::stream),
				of(concatenator.toString()))
				.filter(p -> !p.isBlank());
	}

	private Optional<String> partitionCharacter(char character) {
		if (splicer.test(character)) {
			String value = concatenator.toString();
			concatenator = new StringBuilder();
			return Optional.of(value);
		} else {
			concatenator.append(mapper.apply(character));
			return Optional.empty();
		}
	}
}