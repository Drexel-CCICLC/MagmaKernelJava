package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NativeTest extends CompileTest {
	@Test
	void test() {
		String result = compileOnly("{native val foo ==> void;foo();}");
		assertEquals("{foo();}", result);
	}

	@Test
	void testPointer() {
		String result = compileOnly("{native val run = (~int x) => void; val y = 10; run(y);}");
		assertEquals("{int y=10;run(y);}", result);
	}
}
