package com.meti.node.array;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.Resolver;
import com.meti.node.Node;
import com.meti.node.declare.DeclareParser;
import com.meti.node.primitive.IntParser;
import com.meti.node.primitive.IntResolver;
import com.meti.parse.TreeDeclarations;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayContentParserTest {

	@Test
	void parse() {
		TreeDeclarations declarations = new TreeDeclarations();
		Parser parser = new ParentParser(
				new DeclareParser(declarations),
				new ArrayContentParser(),
				new IntParser()
		);
		Resolver resolver = new ParentResolver(
				new ArrayContentResolver(),
				new IntResolver()
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		Node node = compiler.parse("val array = Array<Int>{3, 4}");
		assertEquals("int array[]={3,4};", node.render());
	}
}