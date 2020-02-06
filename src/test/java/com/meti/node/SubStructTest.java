package com.meti.node;

import com.meti.Compiler;
import com.meti.*;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.VariableParser;
import com.meti.node.declare.VariableResolver;
import com.meti.node.primitive.IntResolver;
import com.meti.node.struct.InvocationParser;
import com.meti.node.struct.ReturnParser;
import com.meti.node.struct.StructUnit;
import com.meti.node.transform.OperationParser;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.CollectionCache;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubStructTest {
	@Test
	void test() {
		Cache cache = new CollectionCache();
		Declarations declarations = new TreeDeclarations();
		Unit structUnit = new StructUnit(declarations, cache);
		Parser parser = new ParentParser(
				structUnit,
				new DeclareParser(declarations),
				new ReturnParser(),
				new OperationParser(),
				new InvocationParser(declarations),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				structUnit,
				new IntResolver(),
				new VariableResolver(declarations)
		);
		Compiler compiler = new UnitCompiler(parser, resolver);
		compiler.parse("""
				            val addTwoNumbers = (Int x, Int y) => Int :{
				                val doOperation ==> Int :{
				                    return x + y;
				                };
				                return doOperation();
				            }
				""");
		assertEquals("int _exitCode=0;" +
				"void *_throw=NULL;" +
				"struct addTwoNumbers{int x;int y;};" +
				"int addTwoNumbers_doOperation(struct addTwoNumbers addTwoNumbers_){" +
				"return addTwoNumbers_.x+addTwoNumbers_.y;" +
				"}" +
				"int addTwoNumbers(int x,int y){" +
				"struct addTwoNumbers addTwoNumbers_={x,y};" +
				"return addTwoNumbers_doOperation(addTwoNumbers_);" +
				"}" +
				"int main(){" +
				"return _exitCode;" +
				"}", cache.render());
	}
}
