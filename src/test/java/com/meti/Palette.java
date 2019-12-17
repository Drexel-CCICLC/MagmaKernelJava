package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Palette extends CompileTest {
	@Test
	void testIf() {
		String result = compiler.compile("if(true){val x = 10;}");
		assertEquals("if(true){var a0=10;}", result);
	}


}
