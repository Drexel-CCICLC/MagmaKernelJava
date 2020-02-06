package com.meti.node.primitive;

import com.meti.Parser;
import com.meti.exception.ParseException;
import com.meti.node.primitive.chars.CharParser;
import com.meti.node.primitive.ints.IntParser;
import com.meti.util.ParentParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParentParserTest {

	@Test
	void parse() throws ParseException {
		Parser parser = new ParentParser(
				new IntParser(),
				new CharParser()
		);
		assertEquals("'x'", parser.parse("'x'", null).orElseThrow().render());
		assertEquals("10", parser.parse("10", null).orElseThrow().render());
	}
}