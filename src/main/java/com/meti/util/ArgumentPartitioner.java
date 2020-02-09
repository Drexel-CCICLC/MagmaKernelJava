package com.meti.util;

import java.util.ArrayList;
import java.util.List;

public class ArgumentPartitioner implements Partitioner {
	private final String content;

	public ArgumentPartitioner(String content) {
		this.content = content;
	}

	@Override
	public List<String> partition() {
		List<String> partitions = new ArrayList<>();
		StringBuilder buffer = new StringBuilder();
		int depth = 0;
		for (char c : content.toCharArray()) {
			if (',' == c && 0 == depth) {
				partitions.add(buffer.toString());
				buffer = new StringBuilder();
			} else {
				if ('(' == c) depth++;
				if (')' == c) depth--;
				buffer.append(c);
			}
		}
		partitions.add(buffer.toString());
		return partitions;
	}
}
