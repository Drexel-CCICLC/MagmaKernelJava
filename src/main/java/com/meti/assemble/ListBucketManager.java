package com.meti.assemble;

import com.meti.util.PredicateStreamSplitter;

import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class ListBucketManager<T> implements BucketManager<T> {
	private final List<? extends Bucket<T>> buckets;
	private Bucket<T> current;
	private int currentIndex;

	@SafeVarargs
	ListBucketManager(Bucket<T>... buckets) {
		this(List.of(buckets));
	}

	private ListBucketManager(List<? extends Bucket<T>> buckets) {
		this.buckets = buckets;
		this.current = buckets.get(0);
		this.currentIndex = 0;
	}

	private void advance(T token) {
		currentIndex++;
		if (currentIndex >= buckets.size()) {
			return;
		}
		current = buckets.get(currentIndex);
		put(token);
	}

	@Override
	public boolean allPresent(int... indices) {
		return IntStream.of(indices)
                .mapToObj((IntFunction<Bucket<T>>) buckets::get)
				.map(Bucket::stream)
				.mapToLong(Stream::count)
				.allMatch(value -> value > 0);
	}

	@Override
	public boolean allPresent() {
		return buckets.stream()
				.map(Bucket::stream)
				.mapToLong(Stream::count)
				.allMatch(value -> value > 0);
	}

	@Override
	public Stream<T> at(int index) {
		return buckets.get(index).stream();
	}

	@Override
	public Stream<Stream<T>> split(int index, Predicate<? super T> predicate) {
		return new PredicateStreamSplitter<T>(predicate).split(at(index));
	}

	@Override
	public void put(T token) {
		boolean result = current.append(token);
		if (!result) {
			advance(token);
		}
	}
}
