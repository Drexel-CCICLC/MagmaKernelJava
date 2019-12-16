package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
	@Test
	void failed() {
		Parser parser = new FactoryParser();
		assertThrows(IllegalStateException.class, () -> parser.parse(""));
	}

	@Test
	void parse() {
		Parser parser = new FactoryParser(new PrimitiveNodeFactory());
		String actual = parser.parse("10")
				.compile(new IncrementAliaser(),  new ListNodeTree());
		assertEquals("10", actual);
	}
}