package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest extends CompileTest {
	@Test
	void block() {
		String result = compiler.compile("val test=() => void:{val x=20;}");
		assertEquals("var b1=function(){var a0=20;};", result);
	}

	@Test
	void blockWithSpaces() {
		String result = compiler.compile("val test= () => void  : {val x=20;}");
		assertEquals("var b1=function(){var a0=20;};", result);
	}

	@Test
	void content() {
		String result = compiler.compile("val test=() => void:val x=10");
		assertEquals("var b1=function(){var a0=10;};", result);
	}

	@Test
	void empty() {
		String result = compiler.compile("val empty=() => void:{}");
		assertEquals("var a0=function(){};", result);
	}

	@Test
	void param() {
		String result = compiler.compile("val test=(string some) => void:{}");
		assertEquals("var b1=function(a0){};", result);
	}

	@Test
	void twoParam() {
		String result = compiler.compile("val test=(string some0, string some1) => void:{}");
		assertEquals("var c2=function(a0,b1){};", result);
	}
}
