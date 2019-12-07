package com.meti.magma;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableTest extends MagmaTest {
	@Test
	void variables() {
		String content = "val test0 = 10; val test1 = test0;";
		String result = compile(content);
		assertEquals("var a0=10;var b1=a0;", result);
	}
}
