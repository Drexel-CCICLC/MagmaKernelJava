package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileNodeFactoryTest {

	@Test
	void parse() {
		var result = new WhileNodeFactory()
				.parse("while(true){val x=10;}", new FactoryParser(
						new BlockNodeFactory(),
						new DeclareNodeFactory(new ListNodeTree()),
						new PrimitiveNodeFactory()
				), null)
				.orElseThrow()
				.compile(new IncrementAliaser());
		assertEquals("while(true){var a0=10;}", result);
	}
}