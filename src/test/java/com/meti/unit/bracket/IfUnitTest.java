package com.meti.unit.bracket;

import com.meti.unit.CompoundUnit;
import com.meti.unit.SimpleData;
import com.meti.unit.UnitCompiler;
import com.meti.unit.value.OperationUnit;
import com.meti.unit.value.PrimitiveUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfUnitTest {
	@Test
	void withEquals(){
		String result = new IfUnit()
				.parse("if(5 == 5){};", new UnitCompiler(new CompoundUnit(
						new BlockUnit(),
						new OperationUnit(),
						new PrimitiveUnit(new SimpleData())
				)))
				.orElseThrow();
		assertEquals("if(5===5){}", result);
	}

	@Test
	void parse() {
		String result = new IfUnit()
				.parse(" if(true){};", new UnitCompiler(new CompoundUnit(
						new BlockUnit(),
						new PrimitiveUnit(new SimpleData())
				)))
				.orElseThrow();
		assertEquals("if(true){}", result);
	}
}