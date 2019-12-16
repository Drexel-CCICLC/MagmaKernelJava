package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElseNodeFactoryTest {

	@Test
	void parse() {
		var result = new ElseNodeFactory()
				.parse("else{}", new FactoryParser(new BlockNodeFactory()), null)
				.orElseThrow()
				.compile(new IncrementAliaser());
		assertEquals("else{}", result);
	}
}