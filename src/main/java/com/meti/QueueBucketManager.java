package com.meti;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueBucketManager implements BucketManager {
	private final Queue<Bucket> input;
	private final Queue<Bucket> output;

	public QueueBucketManager(Bucket... buckets) {
		this(List.of(buckets));
	}

	@Override
	public boolean isValid() {
		return input.isEmpty();
	}

	public QueueBucketManager(Collection<Bucket> input) {
		this.input = new LinkedList<>(input);
		this.output = new LinkedList<>();
	}

	@Override
	public String next() {
		return output.poll().collect();
	}

	@Override
	public BucketManager pour(String value) {
		Bucket bucket = input.poll();
		for (char c : value.toCharArray()) {
			while (!bucket.pour(c)) {
				output.add(bucket);
				bucket = input.poll();
			}
		}
		output.add(bucket);
		return this;
	}
}
