package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimitiveTest extends CompileTest {
	@Test
	void testEmpty() {
		assertEquals("int main(){return 0;}", compile(""));
	}

	@Test
	void testInt() {
		assertEquals("int main(){5return 0;}", compile("5"));
	}
}
