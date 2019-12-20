package com.meti;

import org.junit.jupiter.api.Test;

class Palette extends CompileTest {
	@Test
	void assign() {
		String result = compiler.compile("val a = 10; val b = 20; var error = \"\";error = a + \" does not equal \" +" +
				" b;");

	}
}
