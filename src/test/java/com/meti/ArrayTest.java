package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayTest extends InterpretedTest {
	@Test
	void test() throws IOException, InterruptedException {
		String output = interpreter.run("""
				native val printf = (String format, Any value) => Void;
				val main = () => Int :{
					val array = Array<Int>(10);
					array[5] = 420;
					printf("%i", array[5]);
					delete array;
					return 0;
				};
				            """);
		assertEquals("420", output);
	}
}
