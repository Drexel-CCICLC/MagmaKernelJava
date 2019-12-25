package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Palette extends CompileTest {
	@Test
	void assign() {
		Assertions.assertDoesNotThrow(() -> {
			compiler.compile("""
			val a = 10;
			val b = 20;
			var error = "";
			if(a == b) {
		        error = a + " does not equal " + b;
		    };
			""");
		});
	}
}
