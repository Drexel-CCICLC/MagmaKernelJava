package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VariableTest extends CompileTest {
	@Test
	void test() {
		String result = compileOnly("{val x = 10;x}");
		assertEquals("{int x=10;x}", result);
	}
}
