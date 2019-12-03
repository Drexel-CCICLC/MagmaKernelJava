package com.meti.assemble;

import java.util.List;
import java.util.stream.Stream;

public interface BucketManager<T> {
	Stream<T> at(int index);
	List<Stream<T>> split(int index);
}
