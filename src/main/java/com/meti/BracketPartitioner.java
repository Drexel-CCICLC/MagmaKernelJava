package com.meti;

import java.util.ArrayList;
import java.util.Collection;

public class BracketPartitioner implements Partitioner {
	private final Depth depth = Depth.create();
	private final Collection<String> partitions = new ArrayList<>();
	private StringBuilder current = new StringBuilder();

	public static Partitioner create() {
		return new BracketPartitioner();
	}

	@Override
	public Collection<String> partition(String value) {
		for (char c : value.toCharArray()) {
			switch (c) {
				case ';':
					if (depth.isLevel()) completePartition();
					break;
				case '{':
					current.append('{');
					depth.sink();
					break;
				case '}':
					current.append('}');
					if (depth.isClose()) {
						depth.surface();
						completePartition();
					} else {
						depth.surface();
					}
					break;
				default:
					current.append(c);
					break;
			}
		}
		partitions.add(current.toString());
		partitions.removeIf(String::isBlank);
		return partitions;
	}

	private void completePartition() {
		partitions.add(current.toString());
		current = new StringBuilder();
	}
}
