package com.meti.feature.core;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimitiveTest {
	@Test
	void testChar() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("'x'");
		assertEquals("'x'", result);
	}

	@Test
	void testDouble() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("10D");
		assertEquals("10d", result);
	}

	@Test
	void testFloat() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("10F");
		assertEquals("10f", result);
	}

	@Test
	void testInt() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("10");
		assertEquals("10", result);
	}

	@Test
	void testLong() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("10L");
		assertEquals("10L", result);
	}

	@Test
	void testShort() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("10S");
		assertEquals("10", result);
	}
}
