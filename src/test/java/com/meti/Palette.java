package com.meti;

import com.meti.exception.AlreadyExistsException;
import com.meti.unit.CompoundUnit;
import com.meti.unit.DeclareUnit;
import com.meti.unit.PrimitiveUnit;
import com.meti.unit.UnitCompiler;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

class Palette {
	private final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new DeclareUnit(new Counter(), new HashSet<>()),
			new PrimitiveUnit()
	));

	@Test
	void alreadyDeclared() {
		assertThrows(AlreadyExistsException.class, () -> compiler.compile("val x = 10; val x = 20;"));
	}

	@Test
	void declare() {
		assertEquals("var a0=10;", compiler.compile("val x = 10;"));
	}

	@Test
	void declareMultiple() {
		assertEquals("var a0=10;var b1=20;", compiler.compile("val x = 10; val y = 20;"));
	}

	@Test
	void declareOther() {
		assertEquals("var a0=5;", compiler.compile("val y = 5;"));
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
	void name() {
		Set<String> declarations = new HashSet<>();
		Compiler name = new UnitCompiler(new DeclareUnit(new Counter(), declarations));
		name.compile("val x = 10;");
		assertIterableEquals(singleton("x"), declarations);
	}
}
