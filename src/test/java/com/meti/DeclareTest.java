package com.meti;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeclareTest extends CompiledTest {
	@Test
	void declare() {
		Map<String, Declaration> declarations = new HashMap<>();
		Compiler compiler = new UnitCompiler(new MagmaUnit(new Declarations(declarations)));
		Node node = compiler.compile("var x = 10");
		assertTrue(node.render().isBlank());
		assertTrue(declarations.containsKey("x"));
		Declaration declaration = declarations.get("x");
		assertTrue(declaration.canAssign(PrimitiveType.INT));
		assertEquals("10", declaration.render());
	}
}
