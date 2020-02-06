package com.meti;

import com.meti.core.ParentParser;
import com.meti.core.ParentResolver;
import com.meti.core.UnitCompiler;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.TreeDeclarations;
import com.meti.node.primitive.IntParser;
import com.meti.node.primitive.IntResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeclareParserTest {

	@Test
	void parseDeclaration() throws ParseException {
		Parser rootParser = new ParentParser(
				new DeclareParser(new TreeDeclarations()),
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