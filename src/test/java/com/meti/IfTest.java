package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IfTest extends CompileTest {
	@Test
	void testIf() {
		String result = compiler.compile("if(true){val x = 10;}");
		assertEquals("if(true){var a0=10;}", result);
	}

	@Test
	void testIfElse() {
		String result = compiler.compile("if(true){val x = 10;};else{val y = 20;}");
		assertEquals("if(true){var a0=10;}else{var b1=20;}", result);
	}
}
