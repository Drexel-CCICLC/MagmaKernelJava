package com.meti.magma;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableTest extends MagmaTest {
	@Test
	void variables() {
		String content = "val test0 = 10; val test1 = test0;";
		String result = compile(content);
		assertEquals("int a0=10;int b1=a0;", result);
	}
}
