package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvocationTest extends CompileTest {
	@Test
	void invoke() {
		String result = compiler.compile("val empty=() => void:{};empty();");
		assertEquals("var a0=function(){};a0();", result);
	}

	@Test
	void oneParam(){
		String result = compiler.compile("val one=(int param) => void:{};one(10);");
		assertEquals("var b1=function(a0){};b1(10);", result);
	}

	@Test
	void twoParam() {
		String result = compiler.compile("val two=(int param0, int param1) => void :{};two(10, 20);");
		assertEquals("var c2=function(a0,b1){};c2(10,20);", result);
	}
}
