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
				new InvocationParser(),
				new VariableParser(declarations)
		);
		resolver = new ParentResolver(
				new StructResolver(declarations),
				new IntResolver()
		);
		compiler = new UnitCompiler(parser, resolver);
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
		assertEquals("int _exitCode=0i;" +
				"struct reflect{int x;int(*doOperation)();};" +
				"struct reflect reflect$;" +
				"int reflect_doOperation(){return reflect$.x;}" +
				"int reflect(int x){" +
				"struct reflectreflect_={x,reflect_doOperation};" +
				"reflect$=reflect_;" +
				"return reflect_.doOperation();}" +
				"int main(){return _exitCode;}", cache.render());
	}
}
