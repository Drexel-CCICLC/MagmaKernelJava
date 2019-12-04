package com.meti.assemble;

import java.util.stream.Stream;

public interface Bucket<T> {
	Bucket<T> appendAll(T... values);

	boolean append(T value);

	Stream<T> stream();
}
