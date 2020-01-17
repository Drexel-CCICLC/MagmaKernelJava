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
					val point = Point(3, 4);
					printf("%i", point.getX());
					printf("%i", point.getY());
				};
				   """);
		assertEquals("34", result);
	}
}
