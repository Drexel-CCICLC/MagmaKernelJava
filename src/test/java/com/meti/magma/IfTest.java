package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IfTest extends MagmaTest {
	@Test
	void withElse() {
		String content =
				"if(true){val x = 10;}else{val y = 10;}";
		String result = compile(content);
		assertEquals("if(true){int x=10;}else{int y=10;}", result);
	}
}
