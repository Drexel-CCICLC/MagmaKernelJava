package com.meti.node.struct;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringIndexBufferTest {

	@Test
	void cut() {
		IndexBuffer buffer = new StringIndexBuffer("01234", "2");
		assertEquals("234", buffer.cut(0));
	}

	@Test
	void cutIfPresent() {
	}

	@Test
	void isValid() {

	}

	@Test
	void testIsPresentInvalid() {
		IndexBuffer buffer = new StringIndexBuffer("01234", "5");
		assertFalse(buffer.isPresent(0));
	}

	@Test
	void testIsPresentValid() {
		IndexBuffer buffer = new StringIndexBuffer("01234", "0");
		assertTrue(buffer.isPresent(0));
	}
}