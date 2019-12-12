package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationNodeTest {

	@Test
	void parse() {
		var node = new OperationNodeFactory()
				.parse("10+20", new FactoryParser(new PrimitiveNodeFactory()))
				.orElseThrow();
		var actual = node.compile();
		assertEquals("10+20", actual);
	}
}