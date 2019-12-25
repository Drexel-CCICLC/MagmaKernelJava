package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTest extends CompileTest {
	@Test
	void test() {
		String result = compiler.compile("\"Hello \" + \"World!\"");
		assertEquals("\"Hello \"+\"World!\"", result);
	}

	@Test
	void testSolo() {
		String result = compiler.compile(" \"Hello World!\"  ");
		assertEquals("\"Hello World!\"", result);
	}
}
