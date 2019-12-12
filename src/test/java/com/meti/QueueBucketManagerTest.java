package com.meti;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueueBucketManagerTest {

	@Test
	void next() {
		Bucket b0 = Bucket.build().restrict(2);
		Bucket b1 = Bucket.build();
		List<Bucket> input = List.of(b0, b1);
		BucketManager manager = new QueueBucketManager(input).pour("test");
		assertEquals("te", manager.next());
		assertEquals("st", manager.next());
	}

	@Test
	void pour() {
		Bucket b0 = Bucket.build().restrict(2);
		Bucket b1 = Bucket.build();
		List<Bucket> input = List.of(b0, b1);
		BucketManager manager = new QueueBucketManager(input);
		manager.pour("test");
		assertEquals("te", b0.collect());
		assertEquals("st", b1.collect());
	}
}