package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaHelloWorldTest extends InterpretedTest {
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
