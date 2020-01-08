package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperationTest extends CompileTest {
	@Test
	void test() {
		String result = compileOnly("5 + 5");
		Assertions.assertEquals("5+5", result);
	}
}
