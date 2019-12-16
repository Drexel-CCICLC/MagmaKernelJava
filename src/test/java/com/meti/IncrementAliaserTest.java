package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncrementAliaserTest {

	@Test
	void alias() {
		Aliaser aliaser = new IncrementAliaser();
		assertEquals("a0", aliaser.alias("test"));
		assertEquals("a0", aliaser.alias("test"));
		assertEquals("b1", aliaser.alias("te", "st"));
		assertEquals("b1", aliaser.alias("te", "st"));
	}
}