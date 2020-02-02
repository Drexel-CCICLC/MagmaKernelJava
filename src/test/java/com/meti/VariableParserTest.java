package com.meti;

import com.meti.core.ParentParser;
import com.meti.core.ParentResolver;
import com.meti.core.UnitCompiler;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.declare.Declarations;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.TreeDeclarations;
import com.meti.node.declare.VariableParser;
import com.meti.node.primitive.IntParser;
import com.meti.node.primitive.IntResolver;
import com.meti.node.primitive.IntType;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableParserTest {

	@Test
	void parse() throws ParseException {
		Declarations declarations = new TreeDeclarations();
		declarations.define(IntType.INSTANCE, "test", Collections.emptySet());
		Parser parser = new ParentParser(
				new DeclareParser(new TreeDeclarations()),
				new IntParser(),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				new IntResolver()
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		Node node = compiler.parse("test=10");
		assertEquals("test=10;", node.render());
	}
}