package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.util.Collections.singleton;

class ArrayTest {
	private final Interpreter interpreter = new MagmaInterpreter(singleton("stdio.h"));

	@Test
	void test() throws IOException, InterruptedException {
		interpreter.run("""
				native val printf = (String format, Any value) => Void;
				val main = () => Int :{
					val array = Array<Int>(10);
					array[5] = 420;
					printf("%i", array[5]);
					delete array;
					return 0;
				};
				            """);
	}
}
