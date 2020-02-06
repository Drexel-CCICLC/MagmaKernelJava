package com.meti;

import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.primitive.ints.IntParser;
import com.meti.node.struct.ReturnParser;
import com.meti.util.ParentParser;
import com.meti.util.UnitCompiler;
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