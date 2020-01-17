package com.meti.util;

import java.util.ArrayList;
import java.util.Collection;

public class BracketPartitioner implements Partitioner {
	public static Partitioner create() {
		return new BracketPartitioner();
	}

	@Override
	public Collection<String> partition(String content) {
		Collection<String> partitions = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		int depth = 0;
		for (char c : content.toCharArray()) {
			if (';' == c && 0 == depth) {
				partitions.add(builder.toString());
				builder = new StringBuilder();
			} else {
				if ('{' == c) depth++;
				if ('}' == c) depth--;
				builder.append(c);
			}
		}
		partitions.add(builder.toString());
		partitions.removeIf(String::isBlank);
		return partitions;
	}
}
