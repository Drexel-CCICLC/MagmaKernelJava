package com.meti.declare;

import com.meti.node.other.AnyType;
import com.meti.node.value.primitive.integer.IntType;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TreeDeclarationsTest {

	@Test
	void absolute() {
		Declarations declarations = new TreeDeclarations();
		Declaration expected = declarations.define("main", IntType.INSTANCE);
		Declaration actual = declarations.absolute(Collections.singleton("main"));
		assertSame(expected, actual);
	}

	@Test
	void current() {
		Declarations declarations = new TreeDeclarations();
		declarations.isRoot(declarations.current());
	}

	@Test
	void define() {
		Stack<String> stack = new Stack<>();
		List<Declaration> children = new ArrayList<>();
		TreeDeclarationBuilder builder = TreeDeclarationBuilder.create()
				.withChildren(children)
				.withParameter(false)
				.withStack(stack)
				.withType(null);
		Declarations declarations = new TreeDeclarations(stack, builder);
		Declaration expected = declarations.define("test", IntType.INSTANCE);
		assertEquals(1, children.size());
		assertEquals(expected, children.get(0));
	}

	@Test
	void defineWithAction() {
		Declarations declarations = new TreeDeclarations();
		Declaration child = declarations.define("parent", AnyType.INSTANCE,
				() -> declarations.define("child", AnyType.INSTANCE));
		Optional<Declaration> optional = declarations.absolute(Collections.singleton("parent")).child("child");
		assertTrue(optional.isPresent());
		assertSame(child, optional.get());
	}

	@Test
	void isCurrent() {
	}

	@Test
	void isRoot() {
	}

	@Test
	void parentOf() {
	}

	@Test
	void relative() {
	}

	@Test
	void stream() {
	}

	@Test
	void testDefine1() {
	}

	@Test
	void testDefine2() {
	}
}