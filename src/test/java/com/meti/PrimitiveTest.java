package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimitiveTest {
	private final Compiler compiler = new Compiler();

	@Test
	void testEmpty() {
		assertEquals("int main(){return 0;}", compiler.compile(""));
	}

	@Test
	void testInt() {
		assertEquals("int main(){5;return 0;}", compiler.compile("5"));
	}
}
