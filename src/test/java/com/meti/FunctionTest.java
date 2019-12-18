package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest extends CompileTest {
	@Test
	void block() {
		String result = compiler.compile("val test=():{val x=20;}");
		assertEquals("var b1=function(){var a0=20;};", result);
	}

	@Test
	void blockWithSpaces() {
		String result = compiler.compile("val test= () : {val x=20;}");
		assertEquals("var b1=function(){var a0=20;};", result);
	}

	@Test
	void content() {
		String result = compiler.compile("val test=():val x=10");
		assertEquals("var b1=function(){var a0=10;};", result);
	}

	@Test
	void empty() {
		String result = compiler.compile("val empty=():{}");
		assertEquals("var a0=function(){};", result);
	}

	@Test
	void param() {
		String result = compiler.compile("val test=(some):{}");
		assertEquals("var b1=function(a0){};", result);
	}

	@Test
	void twoParam() {
		String result = compiler.compile("val test=(some0, some1):{}");
		assertEquals("var c2=function(a0,b1){};", result);
	}
}
