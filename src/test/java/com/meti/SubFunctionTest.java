package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.IntResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubFunctionTest {
	private Cache cache;
	private Compiler compiler;
	private Declarations declarations;
	private Parser parser;
	private Resolver resolver;

	@BeforeEach
	void setUp() {
		declarations = new Declarations();
		cache = new Cache();
		parser = new ParentParser(
				new StructParser(declarations, cache),
				new DeclareParser(declarations),
				new ReturnParser(),
				new VariableParser(declarations)
		);
		resolver = new ParentResolver(
				new StructResolver(),
				new IntResolver()
		);
		compiler = new Compiler(parser, resolver);
	}

	@Test
	void simple() throws ParseException {
		compiler.parse("""
				val add = (Int x, Int y) => Int :{
					val doOperation ==> Int : {
						return x + y;
					}
					return doOperation();
				}""");
		assertEquals("int exit_=0i;" +
				"struct add{int x;int y;};" +
				"int add_doOperation(struct add add_){return add_.x + add_.y;}" +
				"int add(int x,int y){struct add add_={x,y};return doOperation(add_);}" +
				"int main(){return exit_;}", cache.render());
	}
}
