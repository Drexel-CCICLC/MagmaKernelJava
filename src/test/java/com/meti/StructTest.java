package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructTest extends CompileTest {
	@Test
	void one() {
		String result = compiler.compileOnly("val some = (int x) => void:{}");
		assertEquals("void some$(int* x){}void(*some)(int*)=&some$;", result);
	}

	@Test
	void test() {
		String result = compiler.compileOnly("val empty = () => void:{}");
		assertEquals("void empty$(){}void(*empty)()=&empty$;", result);
	}
}
