package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvocationTest extends CompileTest {
	@Test
	void invoke() {
		String result = compiler.compile("val empty=():{};val undefined = empty();");
		assertEquals("var a0=function(){};var b1=a0();", result);
	}

	@Test
	void oneParam(){
		String result = compiler.compile("val one=(param):{};val result = one(10);");
		assertEquals("var b1=function(a0){};var c2=b1(10);", result);
	}

	@Test
	void twoParam() {
		String result = compiler.compile("val two=(param0, param1):{};val result = two(10, 20);");
		assertEquals("var c2=function(a0,b1){};var d3=c2(10,20);", result);
	}
}
