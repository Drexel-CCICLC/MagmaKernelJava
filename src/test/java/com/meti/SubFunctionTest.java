package com.meti;

import com.meti.exception.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubFunctionTest {
	@Test
	void parse() {
		Compiler interpreter = new Compiler() {
			@Override
			public Node parse(String value) throws ParseException {
				return null;
			}

			@Override
			public Type resolveName(String name) {
				return null;
			}

			@Override
			public Type resolveValue(String value) {
				return null;
			}
		};
		String result = interpreter.parse("""
					val a = (Int value) => Int :{
					val b = () => Int : {
						return value;
					};
					return b();
				};
				""").render();
		assertEquals("""
				#include <stdio.h>
				#include <stdlib.h>
				int b(void** a_){return *(int*)a_[0];}int a(int value){void** a_=malloc(1*sizeof(void*));a_[0]=&value;return b(a_);}""", result);
	}

}
