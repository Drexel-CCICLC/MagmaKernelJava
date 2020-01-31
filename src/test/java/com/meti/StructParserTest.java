package com.meti;

import com.meti.declare.Declarations;
import com.meti.declare.DeclareParser;
import com.meti.declare.TreeDeclarations;
import com.meti.exception.ParseException;
import com.meti.primitive.IntResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructParserTest {
	private final Cache cache = new CollectionCache();
	private Compiler compiler;

	@Test
	void empty() throws ParseException {
		compiler.parse("val empty =: {}");
		assertEquals("int _exitCode=0;void empty(){}int main(){return _exitCode;}", cache.render());
	}

	@Test
	void parseComplete() throws ParseException {
		Node node = compiler.parse("val complete = (Int value) => Int : {return value;}");
		assertTrue(node.render().isBlank());
		assertEquals("int _exitCode=0;int complete(int value){return value;}int main(){return _exitCode;}", cache.render());
	}

	@BeforeEach
	void setUp() {
		TreeDeclarations declarations = new Declarations();
		Parser parser = new ParentParser(
				new StructParser(declarations, cache),
				new DeclareParser(declarations),
				new ReturnParser(),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				new StructResolver(declarations),
				new IntResolver()
		);
		compiler = new UnitCompiler(parser, resolver);
	}

	@Test
	void withParam() throws ParseException {
		compiler.parse("val accept = (Int some) : {}");
		assertEquals("int _exitCode=0;void accept(int some){}int main(){return _exitCode;}", cache.render());
	}

	@Test
	void withTwoParam() throws ParseException {
		compiler.parse("val accept = (Int one, Int two) : {}");
		assertEquals("int _exitCode=0;void accept(int one,int two){}int main(){return _exitCode;}", cache.render());
	}
}