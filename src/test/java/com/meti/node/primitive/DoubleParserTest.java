package com.meti.node.primitive;

import com.meti.Parser;
import com.meti.node.primitive.doubles.DoubleParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleParserTest {

	@Test
	void parse() {
		Parser parser = new DoubleParser();
		String result = parser.parse("10.5d", null)
				.orElseThrow()
				.render();
		assertEquals("10.5", result);
	}
}