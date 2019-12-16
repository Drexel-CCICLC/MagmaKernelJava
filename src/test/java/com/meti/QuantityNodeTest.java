package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityNodeTest {

	@Test
	void parse() {
		var factory = new QuantityNodeFactory()
				.parse("(10)", new FactoryParser(new PrimitiveNodeFactory()), null)
				.orElseThrow();
		var actual = factory.compile(new IncrementAliaser(),  new ListNodeTree());
		assertEquals("(10)", actual);
	}
}