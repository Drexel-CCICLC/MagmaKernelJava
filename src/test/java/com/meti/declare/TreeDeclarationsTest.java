package com.meti.declare;

import com.meti.node.value.primitive.integer.IntType;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertSame;

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
	void testDefine() {
	}

	@Test
	void testDefine1() {
	}

	@Test
	void testDefine2() {
	}
}