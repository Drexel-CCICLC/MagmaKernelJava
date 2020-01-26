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
				new InvocationParser(),
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
				val reflect = (Int x) => Int :{
					val doOperation ==> Int : {
						return x;
					};
					return doOperation();
				}""");
		assertEquals("int exit_=0i;" +
				"struct reflect{int x;};" +
				"int reflect_doOperation(struct reflect reflect_){return reflect_.x;}" +
				"int reflect(int x){struct reflect reflect_={x};return doOperation(reflect_);}" +
				"int main(){return exit_;}", cache.render());
	}
}
