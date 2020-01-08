package com.meti;

import java.util.stream.Stream;

public interface Partitioner {
	Stream<String> partition(String value);
}
