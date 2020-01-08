package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignTest extends CompileTest {
	@Test
	void test() {
		String result = compileOnly("{var a = 10; a = 20;}");
		assertEquals("{int a=10;a=20;}", result);
	}
}
