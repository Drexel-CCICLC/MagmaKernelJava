package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeclareNodeFactoryTest {

	@Test
	void parse() {
		var root = new DeclareNodeFactory(new ListNodeTree())
				.parse("val x=10", new FactoryParser(new PrimitiveNodeFactory()))
				.orElseThrow();
		String actual = root.compile(new IncrementAliaser());
		assertEquals("var a0=10;", actual);
	}
}