package com.meti.node.transform;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.Unit;
import com.meti.node.Type;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.VariableParser;
import com.meti.node.declare.VariableResolver;
import com.meti.node.primitive.ints.IntParser;
import com.meti.node.primitive.ints.IntResolver;
import com.meti.node.primitive.ints.IntType;
import com.meti.node.primitive.strings.StringParser;
import com.meti.node.primitive.strings.StringResolver;
import com.meti.node.struct.ReturnParser;
import com.meti.node.struct.StructUnit;
import com.meti.node.struct.invoke.InvocationParser;
import com.meti.node.struct.invoke.InvocationResolver;
import com.meti.node.transform.operate.OperationParser;
import com.meti.node.transform.operate.OperationResolver;
import com.meti.parse.Declarations;
import com.meti.parse.TreeDeclarations;
import com.meti.util.CollectionCache;
import com.meti.util.ParentParser;
import com.meti.util.ParentResolver;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationResolverTest {

	@Test
	void resolveInvocation() {
		Cache cache = new CollectionCache();
		Declarations declarations = new TreeDeclarations();
		Unit unit = new StructUnit(declarations, cache);
		Compiler compiler = new UnitCompiler(new ParentParser(
				unit,
				new StringParser(),
				new DeclareParser(declarations),
				new InvocationParser(declarations),
				new ReturnParser(),
				new IntParser(),
				new OperationParser(),
				new VariableParser(declarations)
		), new ParentResolver(
				unit,
				new StringResolver(),
				new InvocationResolver(declarations),
				new IntResolver(),
				new OperationResolver(),
				new VariableResolver(declarations)
		));
		compiler.parse("""
				val length = (String value) => Int :{
						return 0;
					}
				            """);
		compiler.parse("""
				val copy = (String value) => String : {
							val oldLength = length(value);
							val valueLength = oldLength + 1;
						}
				            """);
		Type type = compiler.resolveValue("copy(\"test\")");
		assertEquals(IntType.INSTANCE, type);
	}

	@Test
	void resolveParsedValue() {
		Declarations declarations = new TreeDeclarations();
		Compiler compiler = new UnitCompiler(new ParentParser(
				new DeclareParser(declarations),
				new IntParser()
		), new ParentResolver(
				new IntResolver(),
				new OperationResolver(),
				new VariableResolver(declarations)
		));
		compiler.parse("val x = 10");
		Type type = compiler.resolveValue("x + 5");
		assertEquals(IntType.INSTANCE, type);
	}

	@Test
	void resolveValue() {
		Compiler compiler = new UnitCompiler(null, new ParentResolver(
				new IntResolver(),
				new OperationResolver()
		));
		Type type = compiler.resolveValue("3 + 5");
		assertEquals(IntType.INSTANCE, type);
	}

	@Test
	void resolveValueVariably() {
		Declarations declarations = new TreeDeclarations();
		declarations.define(IntType.INSTANCE, "x", Collections.emptySet());
		Compiler compiler = new UnitCompiler(null, new ParentResolver(
				new IntResolver(),
				new OperationResolver(),
				new VariableResolver(declarations)
		));
		Type type = compiler.resolveValue("x + 5");
		assertEquals(IntType.INSTANCE, type);
	}
}