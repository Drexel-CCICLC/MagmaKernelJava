package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Palette extends CompileTest {
	@Test
	void empty() {
		String result = compiler.compile("val empty=():{}");
		assertEquals("var a0=function(){};", result);
	}

	@Test
	void param() {
		String result = compiler.compile("val test=(some):{}");
		assertEquals("var a0=function(b1){};", result);
	}

	@Test
	void twoParam() {
		String result = compiler.compile("val test=(some0, some1):{}");
		assertEquals("var a0=function(b1,c2){};", result);
	}

	@Test
	void content() {
		String result = compiler.compile("val test=():val x=10");
		assertEquals("var a0=function(){var b1=10;};", result);
	}

	@Test
	void block() {
		String result = compiler.compile("val test=():{val x=20;}");
		assertEquals("var a0=function(){var b1=20;};", result);
	}
}
