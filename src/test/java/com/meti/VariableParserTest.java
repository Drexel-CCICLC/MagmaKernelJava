package com.meti;

import com.meti.core.CollectionCache;
import com.meti.core.ParentParser;
import com.meti.core.ParentResolver;
import com.meti.core.UnitCompiler;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.block.BlockParser;
import com.meti.node.block.BlockResolver;
import com.meti.node.declare.*;
import com.meti.node.primitive.*;
import com.meti.node.struct.*;
import com.meti.node.transform.OperationParser;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableParserTest {

	@Test
	void test() {

		Cache cache = new CollectionCache();
		Declarations declarations = new TreeDeclarations();
		Unit structUnit = new StructUnit(declarations, cache);
		Parser parser = new ParentParser(
				structUnit,
				new IntParser(),
				new BlockParser(),
				new DeclareParser(declarations),
				new ReturnParser(),
				new OperationParser(),
				new StringParser(),
				new InvocationParser(declarations),
				new ThisParser(declarations),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				structUnit,
				new IntResolver(),
				new BooleanResolver(),
				new BlockResolver(declarations),
				new StringResolver(),
				new InvocationResolver(declarations),
				new VariableResolver(declarations),
				new ObjectResolver(declarations)
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		compiler.parse("""
				single val Strings =: {
				    val length = (String string) => Int :{
				        return 0;
				    };

				    val sameSize = (String s0, String s1) => Bool :{
				        val length0 = length(s0);
				        val length1 = length(s1);
				        return length0 == length1;
				    };
				}
				            """);
		assertEquals("int _exitCode=0;" +
				"void *_throw=NULL;" +
				"struct Strings${};" +
				"int Strings$_length(char* string,struct Strings$ Strings$_){" +
				"return 0;}" +
				"int Strings$_sameSize(char* s0,char* s1,struct Strings$ Strings$_){" +
				"int length0=Strings$_length(s0,Strings$_);" +
				"int length1=Strings$_length(s1,Strings$_);" +
				"return length0==length1;}" +
				"struct Strings$ Strings$(){" +
				"struct Strings$ Strings$_={};" +
				"return Strings$_;}" +
				"struct Strings$ Strings={};" +
				"int main(){" +
				"Strings=Strings$();" +
				"return _exitCode;}", cache.render());
	}

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