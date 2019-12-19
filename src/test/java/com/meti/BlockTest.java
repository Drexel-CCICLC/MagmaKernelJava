package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest extends CompileTest {
	@Test
	void multiple() {
		String result = compiler.compile("{val x = 10; val y = 20;}");
		assertEquals("{var a0=10;var b1=20;}", result);
	}

	@Test
	void single() {
		String result = compiler.compile("{val x = 10;}");
		assertEquals("{var a0=10;}", result);
	}
}
