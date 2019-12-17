package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimitiveTest extends CompileTest {
	@Test
	void integer() {
		assertEquals("10", compiler.compile("10"));
	}

	@Test
	void integerOther() {
		assertEquals("5", compiler.compile("5"));
	}
}
