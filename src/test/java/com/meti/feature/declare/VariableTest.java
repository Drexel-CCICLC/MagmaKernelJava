package com.meti.feature.declare;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableTest {
	@Test
	void assign() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("x");
		assertEquals("x", result);
	}
}
