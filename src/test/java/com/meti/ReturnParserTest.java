package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.IntParser;
import com.meti.struct.ReturnParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReturnParserTest {

	@Test
	void parse() throws ParseException {
		Compiler compiler = new UnitCompiler(new ParentParser(
				new ReturnParser(),
				new IntParser()
		), null);
		Node node = compiler.parse("return 10");
		assertEquals("return 10;", node.render());
	}
}