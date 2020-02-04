package com.meti.feature.struct;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StructTest {
	@Test
	void complete() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				val complete = (Int value) => Void :{
				};
				            """);
		assertEquals("void complete(int value){}", result);
	}

	@Test
	void content() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				val content = (Int value) => Void :{
					val x = value;
				};
				            """);
		assertEquals("void content(int value){" +
				"int x=value;}", result);
	}

	@Test
	void missing() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				            val missing =:{
				            }
				""");
		assertEquals("void missing(){}", result);
	}

	@Test
	void multiple() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				val complete = (Int value0, Int value1) => Void :{
				};
				            """);
		assertEquals("void complete(int value0,int value1){}", result);
	}

	@Test
	void returnTest() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				val content = (Int value) => Int :{
					return value;
				};
				            """);
		assertEquals("int content(int value){" +
				"return value;}", result);
	}
}
