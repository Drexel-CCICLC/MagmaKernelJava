package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VariableTest extends CompileTest {
	@Test
	void variables() {
		String result = compiler.compile("val x = 10; val y = x;");
		assertEquals("var a0=10;var b1=a0;", result);
	}
}
