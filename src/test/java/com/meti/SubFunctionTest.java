package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubFunctionTest extends InterpretedTest {
	@Test
	void test() throws IOException, InterruptedException {
		String result = interpreter.run("""
				native val printf = (String format, Any value) => Void;
				val a = (Int value) => Int :{
					val b = () => Int : {
						return value;
					};
					return b();
				};
				val result = a(10);
				printf("%i", result);
				""");
		assertEquals("10", result);
	}
}
