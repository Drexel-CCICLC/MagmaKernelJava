package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldTest {
	private final Interpreter interpreter = new CInterpreter();

	@Test
	void testString() throws IOException, InterruptedException {
		String result = interpreter.runCleanly("""
						#include <stdio.h>
						int main() {
							printf("%s", "Hello World!");
						}
				""");
		assertEquals("Hello World!", result);
	}

	@Test
	void testInteger() throws IOException, InterruptedException {
		String result = interpreter.runCleanly("""
						#include <stdio.h>
						int main() {
							printf("%i", 666);
						}
				""");
		assertEquals("666", result);
	}
}
