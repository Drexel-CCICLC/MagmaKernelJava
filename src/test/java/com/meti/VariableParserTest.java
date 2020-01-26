package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.IntParser;
import com.meti.primitive.IntResolver;
import com.meti.primitive.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableParserTest {

	@Test
	void parse() throws ParseException {
		Declarations declarations = new Declarations();
		declarations.define(IntType.INSTANCE, "test");
		Parser parser = new ParentParser(
				new DeclareParser(new Declarations()),
				new IntParser(),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				new IntResolver()
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		Node node = compiler.parse("test=10");
		assertEquals("test=10i;", node.render());
	}
}