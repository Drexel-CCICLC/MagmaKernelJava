package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructTest extends CompileTest {
	@Test
	void test() {
		String result = compiler.compileOnly("val empty = () => void:{}");
		assertEquals("void empty$(){};void (*empty)()=empty$;", result);
	}
}
