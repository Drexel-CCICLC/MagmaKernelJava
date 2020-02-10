package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.*;
import com.meti.node.block.BlockParser;
import com.meti.node.block.BlockResolver;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.VariableParser;
import com.meti.node.declare.VariableResolver;
import com.meti.node.primitive.ints.IntResolver;
import com.meti.node.primitive.strings.StringParser;
import com.meti.node.primitive.strings.StringResolver;
import com.meti.node.struct.invoke.InvocationParser;
import com.meti.node.struct.invoke.InvocationResolver;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.CollectionCache;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThisParserTest {
	@Test
	void callSibling() {

		Cache cache = new CollectionCache();
		Declarations declarations = new TreeDeclarations();
		Unit structUnit = new StructUnit(declarations, cache);
		Parser parser = new ParentParser(
				structUnit,
				new BlockParser(),
				new DeclareParser(declarations),
				new ReturnParser(),
				new StringParser(),
				new InvocationParser(declarations),
				new ThisParser(declarations),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				structUnit,
				new BlockResolver(declarations),
				new StringResolver(),
				new InvocationResolver(declarations),
				new VariableResolver(declarations),
				new ObjectResolver(declarations)
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		compiler.parse("""
				class val HasTwoMethods =: {
					val methodOne =: {
					};

					val methodTwo =: {
						methodOne();
					};
				}
				            """);
		assertEquals("int _exitCode=0;" +
				"void *_throw=NULL;" +
				"struct HasTwoMethods{};" +
				"void HasTwoMethods_methodOne(struct HasTwoMethods HasTwoMethods_){}" +
				"void HasTwoMethods_methodTwo(struct HasTwoMethods HasTwoMethods_){" +
				"HasTwoMethods_methodOne(HasTwoMethods_);}" +
				"struct HasTwoMethods HasTwoMethods(){" +
				"struct HasTwoMethods HasTwoMethods_={};" +
				"return HasTwoMethods_;}" +
				"int main(){" +
				"return _exitCode;}", cache.render());
	}

	@Test
	void singletonTest() {
		Cache cache = new CollectionCache();
		Declarations declarations = new TreeDeclarations();
		Unit structUnit = new StructUnit(declarations, cache);
		Parser parser = new ParentParser(
				structUnit,
				new BlockParser(),
				new DeclareParser(declarations),
				new ReturnParser(),
				new StringParser(),
				new InvocationParser(declarations),
				new ThisParser(declarations),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				structUnit,
				new BlockResolver(declarations),
				new StringResolver(),
				new InvocationResolver(declarations),
				new VariableResolver(declarations),
				new ObjectResolver(declarations)
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		compiler.parse("""
				single val MySingleton =:{
					val returnAValue = () => String :{
						return "test";
					}
				}
				            """);
		assertEquals("int _exitCode=0;" +
				"void *_throw=NULL;" +
				"struct MySingleton${};" +
				"char* MySingleton$_returnAValue(struct MySingleton$ MySingleton$_){" +
				"return \"test\";}" +
				"struct MySingleton$ MySingleton={};" +
				"struct MySingleton$ MySingleton$(){" +
				"struct MySingleton$ MySingleton$_={};" +
				"return MySingleton$_;}" +
				"int main(){" +
				"MySingleton=MySingleton$();" +
				"return _exitCode;}", cache.render());
	}

	@Test
	void classTest() {
		Cache cache = new CollectionCache();
		Declarations declarations = new TreeDeclarations();
		Unit structUnit = new StructUnit(declarations, cache);
		Parser parser = new ParentParser(
				structUnit,
				new DeclareParser(declarations),
				new ReturnParser(),
				new ThisParser(declarations),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				structUnit,
				new IntResolver(),
				new VariableResolver(declarations),
				new ObjectResolver(declarations)
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		compiler.parse("""
				class val Point = (Int x, Int y) :{
					val getX ==> Int :{
						return x;
					};
					val getY ==> Int :{
						return y;
					};
				}
				            """);
		assertEquals("int _exitCode=0;" +
				"void *_throw=NULL;" +
				"struct Point{int x;int y;};" +
				"int Point_getX(struct Point Point_){return Point_.x;}" +
				"int Point_getY(struct Point Point_){return Point_.y;}" +
				"struct Point Point(int x,int y){" +
				"struct Point Point_={x,y};return Point_;}" +
				"int main(){return _exitCode;" +
				"}", cache.render());
	}


	@Test
	void parse() {
		Cache cache = new CollectionCache();
		Declarations declarations = new TreeDeclarations();
		Unit structUnit = new StructUnit(declarations, cache);
		Parser parser = new ParentParser(
				structUnit,
				new DeclareParser(declarations),
				new ReturnParser(),
				new ThisParser(declarations),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				structUnit,
				new IntResolver(),
				new VariableResolver(declarations),
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
		assertEquals("int _exitCode=0;" +
				"void *_throw=NULL;" +
				"struct Point{int x;int y;};" +
				"int Point_getX(struct Point Point_){return Point_.x;}" +
				"int Point_getY(struct Point Point_){return Point_.y;}" +
				"struct Point Point(int x,int y){" +
				"struct Point Point_={x,y};return Point_;}" +
				"int main(){return _exitCode;" +
				"}", cache.render());
	}
}