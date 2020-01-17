package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayContentTest extends InterpretedTest{
	@Test
	void test() throws IOException, InterruptedException {
		String result = interpreter.run("""
				native val printf = (String format, Any value) => Void;
				val main = () => Int :{
					val array = Array<Int>{3,4};
					printf("%i", array[0]);
					printf("%i", array[1]);
					delete array;
					return 0;
				};
				""");
		assertEquals("34", result);
	}
}