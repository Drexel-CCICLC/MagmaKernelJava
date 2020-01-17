package com.meti.util;

import java.util.Collection;

public interface Partitioner {
	Collection<String> partition(String content);
}
