package com.meti.feature.primitive;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharTest {
	@Test
	void testChar() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("'x'");
		assertEquals("'x'", result);
	}

	@Test
	void testString() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("\"test\"");
		assertEquals("\"test\"", result);
	}
}
