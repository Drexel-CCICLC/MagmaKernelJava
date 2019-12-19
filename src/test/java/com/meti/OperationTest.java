package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTest extends CompileTest {
	@ParameterizedTest
	@ValueSource(strings = {"+", "-", "*", "/", "%", "=="})
	void add(String c) {
		String result = compiler.compile("val x = 5 " + c + " 10;");
		assertEquals("var a0=5" + c + "10;", result);
	}
}
