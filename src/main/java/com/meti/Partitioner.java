package com.meti;

import java.util.Collection;

public interface Partitioner {
	Collection<String> partition(String value);
}
