package com.meti;

import com.meti.exception.AlreadyExistsException;
import com.meti.unit.*;
import com.meti.unit.value.PrimitiveUnit;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

public class DeclareTest extends CompileTest {
	@Test
	void alreadyDeclared() {
		try {
			compiler.compile("val x = 10; val x = 20;");
			fail();
		} catch (Exception e) {
			assertSame(AlreadyExistsException.class, e.getCause().getClass());
		}
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
	void name() {
		Map<String, Declaration> map = new HashMap<>();
		Declarations declarations = new TreeDeclarations(new MutableTreeDeclaration(map, "root"));
		Data data = new SimpleData(declarations);
		Compiler name = new UnitCompiler(new CompoundUnit(
				new DeclareUnit(data),
				new PrimitiveUnit(data)
		));
		name.compile("val x = 10;");
		assertIterableEquals(singleton("x"), map.keySet());
	}
}
