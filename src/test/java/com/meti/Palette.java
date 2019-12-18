package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Palette extends CompileTest {
	@Test
	void subFunction() {
		String result = compiler.compile("val sum = (a, b) : {val compute = () : {return a + b;};};");
		assertEquals("var d3=function(a0,b1){var c2=function(){return a0+b1;};};", result);
	}

	@Test
	void innerFunction(){
		String inner = compiler.compile("val a = 5; val b = 10;val compute = () : {return a + b;};");
		assertEquals("var a0=5;var b1=10;var c2=function(){return a0+b1;};", inner);
	}
}
