package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassTest extends InterpretedTest {
	@Test
	void test() throws IOException, InterruptedException {
		String result = interpreter.run("""
				native val printf = (String format, Any value) => Void;
				val Point = (Int x, Int y) => Point :{
					val getX = () => Int :{
						return x;
					};
					val getY = () => Int :{
						return y;
					};
					return this;
				};
				val main = () => Int :{
					val a = 3;
					val b = 4;
					val point = Point(a, b);
					val x = point.getX();
					val y = point.getY();
					printf("%i", x);
					printf("%i", y);
				};
				   """);
		assertEquals("34", result);
	}
}
