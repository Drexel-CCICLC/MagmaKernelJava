package com.meti;

import com.meti.exception.TypeClashException;
import com.meti.exception.TypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

public class TypeTest extends CompileTest {
	@Test
	void primitives() {
		try {
			compiler.compile("var a = 10; a = \"Hello World!\"");
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertSame(TypeClashException.class, e.getCause().getClass());
		}
	}
}
