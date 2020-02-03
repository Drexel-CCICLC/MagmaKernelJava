package com.meti.feature.operation;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationTest {
	@Test
	void add() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("val x = 3 + 4");
		assertEquals("int x=3+4;", result);
	}
}
