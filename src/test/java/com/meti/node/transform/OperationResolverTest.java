package com.meti.node.transform;

import com.meti.Cache;
import com.meti.Compiler;
import com.meti.Unit;
import com.meti.core.CollectionCache;
import com.meti.core.ParentParser;
import com.meti.core.ParentResolver;
import com.meti.core.UnitCompiler;
import com.meti.node.Type;
import com.meti.node.declare.*;
import com.meti.node.primitive.*;
import com.meti.node.struct.InvocationParser;
import com.meti.node.struct.InvocationResolver;
import com.meti.node.struct.ReturnParser;
import com.meti.node.struct.StructUnit;
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