package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IfTest extends MagmaCompiler {
	@Test
	void withElse() {
		String content = """
		if(true) {
			val x = 10;
		} else {
			val y = 10;
		}
		""";
		String result = compile(content);
		assertEquals("if(true){var a0=10;}else{var b1=10;}", result);
	}
}
