package com.meti.feature.struct;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubStructTest {
	@Test
	void reflect() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				val reflect = (Int value) : Int :{
					val doOperation ==> Int :{
						return value;
					};
					return doOperation();
				};
				            """);
		assertEquals("struct reflect{int value;};" +
				"int reflect_doOperation(struct reflect $reflect){" +
				"return $reflect.value;}" +
				"int reflect(int value){" +
				"struct reflect $reflect={value};" +
				"return reflect_doOperation($reflect);" +
				"}", result);
	}
}
