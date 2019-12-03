package com.meti.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PredicateStreamSplitter<T> implements StreamSplitter<T> {
	private final Predicate<? super T> predicate;

	public PredicateStreamSplitter(Predicate<? super T> predicate) {
		this.predicate = predicate;
	}

	@Override
	public Stream<Stream<T>> split(Stream<? extends T> stream, boolean inclusive) {
		List<T> elements = stream.collect(Collectors.toList());
		Collection<Stream<T>> parent = new ArrayList<>();
		Collection<T> current = new ArrayList<>();
		for (T element : elements) {
			if (predicate.test(element)) {
				if (inclusive) {
					current.add(element);
				}
				parent.add(current.stream());
				current = new ArrayList<>();
			} else {
				current.add(element);
			}
		}
		parent.add(current.stream());
		return parent.stream();
	}
}
