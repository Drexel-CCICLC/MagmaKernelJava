package com.meti;

import com.meti.unit.equals.declare.Declaration;
import com.meti.unit.MagmaUnit;
import com.meti.unit.UnitCompiler;
import com.meti.unit.equals.declare.TreeDeclarations;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeclareTest extends CompiledTest {
	@Test
	void declare() {
		Map<String, Declaration> declarations = new HashMap<>();
		Compiler compiler = new UnitCompiler(new MagmaUnit(new TreeDeclarations(declarations)));
		Node node = compiler.compile("var x = 10");
		assertTrue(node.render().isBlank());
		assertTrue(declarations.containsKey("x"));
		Declaration declaration = declarations.get("x");
		assertTrue(declaration.canAssign(PrimitiveType.INT));
		assertEquals("10", declaration.render());
	}
}
