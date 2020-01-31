package com.meti;

import com.meti.core.ParentParser;
import com.meti.core.ParentResolver;
import com.meti.core.UnitCompiler;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.declare.Declarations;
import com.meti.node.declare.TreeDeclarations;
import com.meti.node.declare.VariableParser;
import com.meti.node.primitive.IntParser;
import com.meti.node.primitive.VoidType;
import com.meti.node.struct.FunctionType;
import com.meti.node.struct.InvocationParser;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvocationParserTest {

	@Test
	void parse() throws ParseException {
		Declarations declarations = new TreeDeclarations();
		declarations.define(new FunctionType(Collections.emptySet(), VoidType.INSTANCE, "a"), "a");
		Parser parser = new ParentParser(new InvocationParser(), new VariableParser(declarations), new IntParser());
		Resolver resolver = new ParentResolver();
		Compiler compiler = new UnitCompiler(parser, resolver);
		Node node = compiler.parse("a(10)");
		assertEquals("a(10)", node.render());
	}
}