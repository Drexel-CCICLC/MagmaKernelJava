package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharTest extends CompileTest {
	@Test
	void test() {
		String result = compileOnly("'a'");
		assertEquals("'a'", result);
	}
}
