package com.meti;

import com.meti.exception.ParseException;
import com.meti.exception.StateException;
import com.meti.node.Node;
import com.meti.node.declare.DeclareParser;
import com.meti.node.primitive.ints.IntParser;
import com.meti.node.primitive.ints.IntResolver;
import com.meti.node.primitive.special.AnyType;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DeclareParserTest {
	@Test
	void alreadyDefined() throws ParseException {
		Declarations declarations = new TreeDeclarations();
		declarations.define(AnyType.INSTANCE, "x", Collections.emptySet());
		Parser rootParser = new ParentParser(
				new DeclareParser(declarations),
				new IntParser()
		);
		Resolver rootResolver = new ParentResolver(
				new IntResolver()
		);
		Compiler compiler = new UnitCompiler(rootParser, rootResolver);
		try {
			compiler.parse("val x = 10");
			fail();
		} catch (ParseException e) {
			assertSame(StateException.class, e.getCause().getClass());
		}
	}

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