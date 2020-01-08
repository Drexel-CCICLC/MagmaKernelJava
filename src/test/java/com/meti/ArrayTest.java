package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTest extends CompileTest {
	@Test
	void test() {
		String result = compileOnly("Array<int>(3)");
		assertEquals("malloc(3*sizeof(int))", result);
	}

	@Test
	void testIndex() {
		String result = compileOnly("{val array = Array<int>(2);val index = array[0];}");
		assertEquals("{int* array=malloc(2*sizeof(int));int index=array[0];}", result);
	}
}
