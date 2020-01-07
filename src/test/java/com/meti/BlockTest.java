package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest extends CompileTest {
	@Test
	void content() {
		String result = compileOnly("{val x = 10;}");
		assertEquals("{int x$=10;int* x=&x$;}", result);
	}

	@Test
	void test() {
		String result = compileOnly("{}");
		assertEquals("{}", result);
	}
}
