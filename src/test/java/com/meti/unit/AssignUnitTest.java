package com.meti.unit;

import com.meti.unit.value.OperationUnit;
import com.meti.unit.value.StringUnit;
import com.meti.unit.value.VariableUnit;
import org.junit.jupiter.api.Test;

import static com.meti.type.PrimitiveType.ANY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignUnitTest {

	@Test
	void parse() {
		Declarations declarations = new MapDeclarations();
		declarations.define(ANY, "a");
		declarations.define(ANY, "b");
		declarations.define(ANY, "error");
		Data data = new SimpleData(declarations);
		String result = new AssignUnit(data)
				.parse("error = a + \" does not equal \" +" +
						" b;", new UnitCompiler(new CompoundUnit(
						new VariableUnit(data),
						new OperationUnit(),
						new StringUnit(data)
				)))
				.orElseThrow();
		assertEquals("a0=b1+\" does not equal \"+c2;", result);
	}
}