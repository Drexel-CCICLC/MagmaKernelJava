package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.*;
import com.meti.core.CollectionCache;
import com.meti.core.ParentParser;
import com.meti.core.ParentResolver;
import com.meti.core.UnitCompiler;
import com.meti.node.declare.Declarations;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.TreeDeclarations;
import com.meti.node.primitive.IntResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThisParserTest {

	@Test
	void parse() {
		Cache cache = new CollectionCache();
		Declarations declarations = new TreeDeclarations();
		Unit structUnit = new StructUnit(declarations, cache);
		Parser parser = new ParentParser(
				structUnit,
				new DeclareParser(declarations),
				new ReturnParser(),
				new ThisParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				structUnit,
				new IntResolver(),
				new ObjectResolver(declarations)
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		compiler.parse("""
				val Point = (Int x, Int y) => Point :{
					val getX ==> Int :{
						return x;
					};
					val getY ==> Int :{
						return y;
					};
					return this;
				}
				            """);
		assertEquals("", cache.render());
	}
}