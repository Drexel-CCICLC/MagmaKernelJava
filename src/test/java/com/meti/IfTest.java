package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IfTest extends CompileTest {
	@Test
	void test0(){
		String result = compiler.compile("val Assertion = () : {\n" +
				"  var error = \"\";\n" +
				"\n" +
				"  val equals = (a, b) : {\n" +
				"    if(a == b) {\n" +
				"      error = a + \" does not equal \" + b;\n" +
				"    };\n" +
				"  };\n" +
				"  return new Assertion;\n" +
				"};");
	}

	@Test
	void testWithEquality() {
		String result = compiler.compile("""
			val a = 10;
			val b = 20;
			if(a == b) {
			};
			""");
		assertEquals("var a0=10;var b1=20;if(a0===b1){}", result);
	}

	@Test
	void testIf() {
		String result = compiler.compile("if(true){val x = 10;}");
		assertEquals("if(true){var a0=10;}", result);
	}

	@Test
	void testIfElse() {
		String result = compiler.compile("if(true){val x = 10;};else{val y = 20;}");
		assertEquals("if(true){var a0=10;}else{var b1=20;}", result);
	}
}
