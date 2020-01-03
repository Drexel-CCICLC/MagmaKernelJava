package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrimitiveTest extends CompiledTest {
	@Test
	void test() {
		assertEquals("5", compile("5"));
	}

	@Test
	void testOther() {
		assertEquals("10", compile("10"));
	}
}
