package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvocationTest extends CompileTest {
	@Test
	void test() {
		String result = compileOnly("{val empty = () => void :{}; empty();}");
		assertEquals("void empty$(){}void(*empty)()=&empty$;{empty();}", result);
	}

	@Test
	void withParam(){
		String result = compileOnly("{val some = (int x) => void : {}; val y = 20; some(y);}");
		assertEquals("void some$(int* x){}void(*some)(int*)=&some$;{int y$=20;int* y=&y$;some(y);}", result);
	}
}
