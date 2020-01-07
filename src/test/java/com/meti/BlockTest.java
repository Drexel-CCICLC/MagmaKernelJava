package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest extends CompileTest {
	@Test
	void content() {
		String result = compileOnly("{val x = 10;}");
		assertEquals("{int x=10;}", result);
	}

	@Test
	void multiple() {
		String result = compileAll("val other = () => void :{\n" +
				"};\n" +
				"\n" +
				"val doSomething = () => void :{\n" +
				"     other();\n" +
				"}");
		assertEquals("void other(){}void doSomething(){other();}int main(){return 0;}", result);
	}

	@Test
	void test() {
		String result = compileOnly("{}");
		assertEquals("{}", result);
	}
}
