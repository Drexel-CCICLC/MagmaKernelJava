package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Palette extends CompileTest {
	@Test
	void add() {
		String result = compiler.compile("val x = 5 + 10;");
		assertEquals("var a0=5+10;", result);
	}
}
