package com.meti;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubFunctionTest extends InterpretedTest {
	@Test
	void parse() {
		String result = interpreter.parse("""
					val a = (Int value) => Int :{
					val b = () => Int : {
						return value;
					};
					return b();
				};
				""").orElseThrow();
		assertEquals("""
				#include <stdio.h>
				#include <stdlib.h>
				int b(void** a_){return *(int*)a_[0];}int a(int value){void** a_=malloc(1*sizeof(void*));a_[0]=&value;return b(a_);}""", result);
	}

	@Test
	void test() throws IOException, InterruptedException {
		/*
		int b(void** a_){
			return *(int*)(a_[0]);
		}
		int a(int value){
			void** a_=malloc(1*sizeof(void*));
			a_[0]=&value;
			free(a_);
			return b(a_);
		}
		 */
		String result = interpreter.run("""
				native val printf = (String format, Any value) => Void;
				val a = (Int value) => Int :{
					val b = () => Int : {
						return value;
					};
					return b();
				};
				val main = () => Int :{
					val result = a(10);
					printf("%i", result);
				}
				""");
		assertEquals("10", result);
	}
}
