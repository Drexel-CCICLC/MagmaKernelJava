package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.IntParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReturnParserTest {

	@Test
	void parse() throws ParseException {
		Compiler compiler = new Compiler(new ParentParser(
				new ReturnParser(),
				new IntParser()
		), null);
		Node node = compiler.parse("return 10");
		assertEquals("return 10i;", node.render());
	}
}