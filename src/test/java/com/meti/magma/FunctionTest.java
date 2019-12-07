package com.meti.magma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionTest extends MagmaCompiler {
	@Test
	void empty() {
		String content = """
			val empty = () => void :{
			}
		""";
		String value = compile(content);
		assertEquals("var a0=function(){};", value);
	}
}
