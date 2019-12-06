package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionTest extends MagmaTest {
	@Test
	void empty() {
		String content = """
			val empty = [] => void :{
			}
		""";
		String value = compile(content);
		assertEquals("public void empty(){}", value);
	}
}
