package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest extends CompileTest {
	@Test
	void content() {
		String result = compileOnly("{val x = 10;}");
		assertEquals("{int x$=10;int* x=&x$;}", result);
	}

	@Test
	void multiple() {
		String result = compileAll("val other = () => void :{\n" +
				"};\n" +
				"\n" +
				"val doSomething = () => void :{\n" +
				"     other();\n" +
				"}");
		assertEquals("void other$(){}" +
				"void(*other)()=&other$;" +
				"void doSomething$(){other();}" +
				"void(*doSomething)()=&doSomething$;" +
				"int main(){return 0;}", result);
	}

	@Test
	void test() {
		String result = compileOnly("{}");
		assertEquals("{}", result);
	}
}
