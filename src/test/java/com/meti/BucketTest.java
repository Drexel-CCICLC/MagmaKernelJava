package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BucketTest {

	@Test
	void build() {
		assertNotNull(Bucket.build());
	}

	@Test
	void collect() {
		String value = Bucket.build().collect();
		assertTrue(value.isEmpty());
	}

	@Test
	void exclude() {
		String actual = Bucket.build()
				.exclude('s')
				.pour("pest")
				.collect();
		assertEquals("pet", actual);
	}

	@Test
	void excludeWhitespace() {
		String actual = Bucket.build()
				.excludeWhitespace()
				.pour("te st")
				.collect();
		assertEquals("test", actual);
	}

	@Test
	void pour() {
		String actual = Bucket.build()
				.pour("test")
				.collect();
		assertEquals("test", actual);
	}

	@Test
	void restrict() {
		String actual = Bucket.build()
				.restrict(2)
				.pour("test")
				.collect();
		assertEquals("te", actual);
	}
}