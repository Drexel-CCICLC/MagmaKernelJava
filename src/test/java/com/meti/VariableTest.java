package com.meti;

import com.meti.unit.Data;
import com.meti.unit.VariableUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VariableTest extends CompileTest {
	@Test
	void notAVariable() {
		assertTrue(new VariableUnit(new Data())
				.parse("5 + 10", null)
				.isEmpty());
	}

	@Test
	void variables() {
		String result = compiler.compile("val x = 10; val y = x;");
		assertEquals("var a0=10;var b1=a0;", result);
	}
}
