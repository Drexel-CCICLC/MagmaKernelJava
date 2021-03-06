package com.meti;

import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.block.BlockParser;
import com.meti.node.block.BlockResolver;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.VariableParser;
import com.meti.node.declare.VariableResolver;
import com.meti.node.primitive.bool.BooleanResolver;
import com.meti.node.primitive.ints.IntParser;
import com.meti.node.primitive.ints.IntResolver;
import com.meti.node.primitive.ints.IntType;
import com.meti.node.primitive.strings.StringParser;
import com.meti.node.primitive.strings.StringResolver;
import com.meti.node.struct.ObjectResolver;
import com.meti.node.struct.ReturnParser;
import com.meti.node.struct.StructUnit;
import com.meti.node.struct.ThisParser;
import com.meti.node.struct.invoke.InvocationParser;
import com.meti.node.struct.invoke.InvocationResolver;
import com.meti.node.transform.operate.OperationParser;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.CollectionCache;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
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
		compiler.parse("single val Strings =: {\n" +
		               "    val length = (String string) => Int :{\n" +
		               "        return 0;\n" +
		               "    };\n" +
		               "\n" +
		               "    val sameSize = (String s0, String s1) => Bool :{\n" +
		               "        val length0 = length(s0);\n" +
		               "        val length1 = length(s1);\n" +
		               "        return length0 == length1;\n" +
		               "    };\n" +
		               "}\n");
		assertEquals("int _exitCode=0;" +
		             "void *_throw=NULL;" +
		             "struct Strings${};" +
		             "int Strings$_length(char* string,struct Strings$ Strings$_){" +
		             "return 0;}" +
		             "int Strings$_sameSize(char* s0,char* s1,struct Strings$ Strings$_){" +
		             "int length0=Strings$_length(s0,Strings$_);" +
		             "int length1=Strings$_length(s1,Strings$_);" +
		             "return length0==length1;}" +
		             "struct Strings$ Strings={};" +
				"struct Strings$ Strings$(){" +
				"struct Strings$ Strings$_={};" +
				"return Strings$_;}" +
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