package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReturnTest extends CompileTest {
	@Test
	void testReturn() {
		String result = compiler.compile("return 5");
		assertEquals("return 5;", result);
	}
}
