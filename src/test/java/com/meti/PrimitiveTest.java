package com.meti;

import com.meti.unit.PrimitiveUnit;
import com.meti.unit.Unit;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimitiveTest extends CompileTest {
	@Test
	void booleans() {
		String result = compiler.compile("true");
		assertEquals("true", result);
	}

	@Test
	void integer() {
		assertEquals("10", compiler.compile("10"));
	}

	@Test
	void integerOther() {
		assertEquals("5", compiler.compile("5"));
	}

	@Test
	void notAnInteger() {
		Unit unit = new PrimitiveUnit();
		Optional<String> optional = unit.parse("x", null);
		assertTrue(optional.isEmpty());
	}
}
