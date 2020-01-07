package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeclareTest extends CompileTest {
	@Test
	void test() {
		assertEquals("int main(){int x$=20;int* x=&x$;return 0;}", compileAll("val x = 20"));
	}

	@Test
	void test2() {
		assertEquals("int main(){int x$=10;int* x=&x$;return 0;}", compileAll("val x = 10"));
	}

	@Test
	void test3() {
		assertEquals("int main(){int y$=20;int* y=&y$;return 0;}", compileAll("val y = 20"));
	}
}
