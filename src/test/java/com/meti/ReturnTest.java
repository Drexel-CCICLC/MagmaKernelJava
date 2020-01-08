package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReturnTest extends CompileTest {
	@Test
	void testReturn() {
		String result = compileOnly("return 0");
		assertEquals("return 0;", result);
	}

	@Test
	void returnInFunction() {
		String result = compileOnly("val empty = () => void : {return 0;}");
		assertEquals("void empty(){return 0;}", result);
	}
}
