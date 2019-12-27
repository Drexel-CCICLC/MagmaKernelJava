package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NativeTest extends CompileTest {
	@Test
	void nativeInvoke() {
		String result = compiler.compile("native val writeInt = (int value) => void; writeInt(10);");
		assertEquals("writeInt(10);", result);
	}

	@Test
	void nativeMethod() {
		String result = compiler.compile("native val writeString = (string value) => void");
		assertTrue(result.isBlank());
	}
}
