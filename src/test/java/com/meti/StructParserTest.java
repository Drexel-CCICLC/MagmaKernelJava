package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.IntResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructParserTest {
	private Cache cache;
	private Compiler compiler;
	private Declarations declarations;
	private Parser parser;
	private Resolver resolver;

	@Test
	void empty() throws ParseException {
		compiler.parse("val empty =: {}");
		assertEquals("int _exit=0i;void empty(){}int main(){return _exit;}", cache.render());
	}

	@Test
	void parseComplete() throws ParseException {
		Node node = compiler.parse("val complete = (Int value) => Int : {return value;}");
		assertTrue(node.render().isBlank());
		assertEquals("int _exit=0i;int complete(int value){return value;}int main(){return _exit;}", cache.render());
	}

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
				new StructResolver(declarations),
				new IntResolver()
		);
		compiler = new Compiler(parser, resolver);
	}

	@Test
	void withParam() throws ParseException {
		compiler.parse("val accept = (Int some) : {}");
		assertEquals("int _exit=0i;void accept(int some){}int main(){return _exit;}", cache.render());
	}

	@Test
	void withTwoParam() throws ParseException {
		compiler.parse("val accept = (Int one, Int two) : {}");
		assertEquals("int _exit=0i;void accept(int one,int two){}int main(){return _exit;}", cache.render());
	}
}