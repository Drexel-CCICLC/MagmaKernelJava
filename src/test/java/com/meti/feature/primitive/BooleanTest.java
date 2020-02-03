package com.meti.feature.primitive;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BooleanTest {
	@Test
	void testFalse() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("false");
		assertEquals("0", result);
	}

	@Test
	void testTrue() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("true");
		assertEquals("1", result);
	}
}
