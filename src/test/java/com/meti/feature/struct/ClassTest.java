package com.meti.feature.struct;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassTest {
	@Test
	void classKeyword() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				class val Point = (Float x, Float y) :{
					val getX ==> Int: {
						return x;
					};
					val getY ==> Int: {
						return y;
					};
				}:
				            """);
		assertEquals("struct Point{float x;float y;};" +
				"int Point_getX(struct Point $Point){" +
				"return $Point.x}" +
				"int Point_getY(struct Point $Point){" +
				"return $Point.y}" +
				"struct Point _Point(float x,float y){" +
				"struct Point Point$={x,y};" +
				"return Point$;}" +
				"void Point_(struct Point Point$){" +
				"}", result);
	}

	@Test
	void noKeywords() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				val Point = (Float x, Float y) => Point :{
					val getX ==> Int: {
						return x;
					};
					val getY ==> Int: {
						return y;
					};
					return this;
				}:
				            """);
		assertEquals("struct Point{float x;float y;};" +
				"int Point_getX(struct Point $Point){" +
				"return $Point.x}" +
				"int Point_getY(struct Point $Point){" +
				"return $Point.y}" +
				"struct Point _Point(float x,float y){" +
				"struct Point Point$={x,y};" +
				"return Point$;}" +
				"void Point_(struct Point Point$){" +
				"}", result);
	}

	@Test
	void singleKeyword() {
		Compiler compiler = new Compiler();
		String result = compiler.compile("""
				single val Internal =: {
					val doNothing =: {
					};
				};
				            """);
		assertEquals("struct Internal{};" +
				"void Internal_doNothing(struct Internal $Interal){}" +
				"struct Internal _Internal(){" +
				"struct Internal $Internal={};" +
				"return $Internal;}" +
				"struct Internal Internal=_Internal();", result);
	}
}
