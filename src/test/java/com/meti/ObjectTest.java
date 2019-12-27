package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectTest extends CompileTest {
	@Test
	void invocation() {
		String result = compiler.compile(
				"val Point = (int x, int y) => Point: {" +
						"val getX = () => int: return x;" +
						"val getY = () => int: return y;" +
						"return new Point;" +
						"};" +
						"val a = Point(3,4);" +
						"val b = a.getX();");
		assertEquals("var e4=function(a0,c2){var b1=function(){return a0;};var d3=function(){return c2;};return [a0,c2,b1,d3];};var f5=e4(3,4);var g6=f5[2]();", result);
	}

	@Test
	void testNew() {
		String result = compiler.compile(
				"val Point = (int x, int y) => Point: {" +
						"val getX = () => int: return x;" +
						"val getY = () => int: return y;" +
						"return new Point;" +
						"}");
		assertEquals("var e4=function(a0,c2){var b1=function(){return a0;};var d3=function(){return c2;};return [a0,c2,b1,d3];};", result);
	}
}
