package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaHelloWorldTest {
	private final Interpreter interpreter = new MagmaInterpreter(singleton("stdio.h"));

	@Test
	void test() throws IOException, InterruptedException {
		String result = interpreter.run("""
				native val printf = (String format, Any value) => Void;
				val main = () => Int :{
					printf("%s", "Hello World!");
					return 0;
				};
				""");
		assertEquals("Hello World!", result);
	}
}
