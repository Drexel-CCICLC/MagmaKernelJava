package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Palette extends CompileTest {
	@Test
	void invocation() {
		String result = compiler.compile(
				"val Point = (x, y) : {" +
						"val getX = () : return x;" +
						"val getY = () : return y;" +
						"return new Point;" +
						"};" +
						"val a = Point(3,4);" +
						"val b = a.getX();");
		assertEquals("var e4=function(a0,b1){var c2=function(){return a0;};var d3=function(){return b1;};return [c2," +
				"d3];};var f5=e4(3,4);var g6=f5[0]();", result);
	}

	@Test
	void testNew() {
		String result = compiler.compile(
				"val Point = (x, y) : {" +
						"val getX = () : return x;" +
						"val getY = () : return y;" +
						"return new Point;" +
						"}");
		assertEquals("var e4=function(a0,b1){var c2=function(){return a0;};var d3=function(){return b1;};return [c2," +
				"d3];};", result);
	}
}
