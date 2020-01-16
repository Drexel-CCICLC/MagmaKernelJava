package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTest {
	private final Interpreter interpreter = new MagmaInterpreter(List.of(
			"stdio.h",
			"stdlib.h"
	));

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
