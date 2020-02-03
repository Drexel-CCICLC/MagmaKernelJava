package com.meti.feature.declare;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeclareTest {
	@Test
	void assign() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("val x = 10");
		assertEquals("x=10;", result);
	}

	@Test
	void declare() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("val x = 10");
		assertEquals("int x=10;", result);
	}
}
