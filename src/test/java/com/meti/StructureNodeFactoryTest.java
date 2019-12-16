package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StructureNodeFactoryTest {

	@Test
	void parse() {
		var result = new StructureNodeFactory(new ListNodeTree())
				.parse("(x int,y int)=>int:{}", new FactoryParser(
						new BlockNodeFactory(),
						new PrimitiveNodeFactory()
				))
				.orElseThrow()
				.compile(new IncrementAliaser());
		assertEquals("function(a0,b1){}", result);
	}
}