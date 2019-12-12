package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityNodeTest {

	@Test
	void parse() {
		var factory = new QuantityNodeFactory()
				.parse("(10)", new FactoryParser(new PrimitiveNodeFactory()))
				.orElseThrow();
		var actual = factory.compile();
		assertEquals("(10)", actual);
	}
}