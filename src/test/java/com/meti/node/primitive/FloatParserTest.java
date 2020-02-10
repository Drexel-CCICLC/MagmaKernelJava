package com.meti.node.primitive;

import com.meti.Parser;
import com.meti.node.primitive.floats.FloatParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatParserTest {

	@Test
	void parse() {
		Parser parser = new FloatParser();
		String result = parser.parse("10.2f", null)
				.orElseThrow()
				.render();
		assertEquals("10.2", result);
	}
}