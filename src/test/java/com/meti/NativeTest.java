package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NativeTest extends CompileTest {
	@Test
	void nativeInvoke() {
		String result = compiler.compile("native val writeInt = (value); val result = writeInt(10);");
		assertEquals("var b1=writeInt(10);", result);
	}

	@Test
	void nativeMethod() {
		String result = compiler.compile("native val writeString = (value)");
		assertTrue(result.isBlank());
	}
}
