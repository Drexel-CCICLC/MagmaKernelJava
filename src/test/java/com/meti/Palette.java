package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Palette extends CompileTest {
	@Test
	void nativeMethod() {
		String result = compiler.compile("native val writeString = (value)");
		assertTrue(result.isBlank());
	}

	@Test
	void nativeInvoke() {
		String result = compiler.compile("native val writeInt = (value); writeInt(10)");
		assertEquals("a0(10);", result);
	}
}
