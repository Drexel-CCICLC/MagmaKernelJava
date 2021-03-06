package com.meti;

import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.declare.VariableParser;
import com.meti.node.declare.VariableResolver;
import com.meti.node.primitive.ints.IntParser;
import com.meti.node.primitive.special.VoidType;
import com.meti.node.struct.invoke.InvocationParser;
import com.meti.node.struct.type.FunctionTypeImpl;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvocationParserTest {

	@Test
	void parse() throws ParseException {
		Declarations declarations = new TreeDeclarations();
		declarations.define(new FunctionTypeImpl(Collections.emptySet(), VoidType.INSTANCE, "a"), "a",
				Collections.emptySet());
		Parser parser = new ParentParser(
				new InvocationParser(declarations),
				new IntParser(),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(new VariableResolver(declarations));
		Compiler compiler = new UnitCompiler(parser, resolver);
		Node node = compiler.parse("a(10)");
		assertEquals("a(10);", node.render());
	}
}