package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.IntParser;
import com.meti.primitive.IntResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeclareParserTest {

	@Test
	void parseDeclaration() throws ParseException {
		Parser rootParser = new ParentParser(
				new DeclareParser(new Declarations()),
				new IntParser()
		);
		Resolver rootResolver = new ParentResolver(
				new IntResolver()
		);
		Compiler compiler = new UnitCompiler(rootParser, rootResolver);
		Node node = compiler.parse("val x = 10");
		assertEquals("int x=10;", node.render());
	}
}