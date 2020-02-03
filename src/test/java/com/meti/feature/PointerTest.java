package com.meti.feature;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointerTest {
	@Test
	void dereference() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("val y = *x");
		assertEquals("int y = *x;", result);
	}

	@Test
	void reference() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("val y = &x");
		assertEquals("int* y = &x;", result);
	}
}
