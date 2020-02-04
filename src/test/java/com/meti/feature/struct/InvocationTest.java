package com.meti.feature.struct;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvocationTest {
	@Test
	void notVoid() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("function(10)");
		assertEquals("function(10)", result);
	}

	@Test
	void testVoid() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("function(10)");
		assertEquals("function(10);", result);
	}
}
