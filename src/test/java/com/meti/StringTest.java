package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTest extends CompileTest {
	@Test
	void declared() {
		String result = compileOnly("val x = \"Hello World!\"");
		assertEquals("char* x=\"Hello World!\";", result);
	}

	@Test
	void test() {
		String result = compileOnly("\"Hello World!\"");
		assertEquals("\"Hello World!\"", result);
	}
}
