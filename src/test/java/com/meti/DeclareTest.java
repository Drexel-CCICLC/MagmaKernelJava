package com.meti;

import com.meti.exception.AlreadyExistsException;
import com.meti.unit.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

public class DeclareTest extends CompileTest {
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
	void name() {
		Aliaser aliaser = new SimpleAliaser();
		Map<String, Declarations.Declaration> map = new HashMap<>();
		Declarations declarations = new Declarations(map);
		Compiler name = new UnitCompiler(new CompoundUnit(
				new DeclareUnit(new Data(aliaser, declarations)),
				new PrimitiveUnit()
		));
		name.compile("val x = 10;");
		assertIterableEquals(singleton("x"), map.keySet());
	}
}
