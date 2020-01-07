package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructTest extends CompileTest {
	@Test
	void one() {
		String result = compileOnly("val some = (int x) => void:{}");
		assertEquals("void some$(int* x){}void(*some)(int*)=&some$;", result);
	}

	@Test
	void test() {
		String result = compileOnly("val empty = () => void:{}");
		assertEquals("void empty$(){}void(*empty)()=&empty$;", result);
	}

	@Test
	void testFull() {
		String result = compileAll("val test = () => int:{}");
		assertEquals("int test$(){}int(*test)()=&test$;int main(){return 0;}", result);
	}
}
