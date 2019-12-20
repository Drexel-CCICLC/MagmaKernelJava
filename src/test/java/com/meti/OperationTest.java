package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTest extends CompileTest {
	@Test
	void individualEquals() {
		assertEquals("5===5", compiler.compile("5==5"));
	}

	@ParameterizedTest
	@ValueSource(strings = {"+", "-", "*", "/", "%"})
	void add(String c) {
		String result = compiler.compile("val x = 5 " + c + " 10;");
		assertEquals("var a0=5" + c + "10;", result);
	}

	@Test
	void equals() {
		String result = compiler.compile("val x = 5 == 10;");
		assertEquals("var a0=5===10;", result);
	}
}
