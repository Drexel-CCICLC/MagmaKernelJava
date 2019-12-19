package com.meti.unit.bracket;

import com.meti.Compiler;
import com.meti.unit.CompoundUnit;
import com.meti.unit.UnitCompiler;
import com.meti.unit.value.PrimitiveUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfUnitTest {

	@Test
	void parse() {
		String result = new IfUnit()
				.parse(" if(true){};", new UnitCompiler(new CompoundUnit(
						new BlockUnit(),
						new PrimitiveUnit()
				)))
				.orElseThrow();
		assertEquals("if(true){}", result);
	}
}