package com.meti.declare;

import com.meti.node.value.primitive.integer.IntType;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TreeDeclarationsTest {

	@Test
	void absolute() {
		Declarations declarations = new TreeDeclarations();
		Declaration expected = declarations.define("main", IntType.INSTANCE);
		Declaration actual = declarations.absolute(Collections.singleton("main"));
		assertSame(expected, actual);
	}
}