package com.meti.util;

import java.util.stream.Stream;

public interface StreamSplitter<T> {
	default Stream<Stream<T>> split(Stream<? extends T> stream) {
		return split(stream, false);
	}

	Stream<Stream<T>> split(Stream<? extends T> stream, boolean inclusive);
}
