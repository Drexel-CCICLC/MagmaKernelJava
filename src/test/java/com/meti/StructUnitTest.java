package com.meti;

import com.meti.core.CollectionCache;
import com.meti.core.ParentParser;
import com.meti.core.ParentResolver;
import com.meti.core.UnitCompiler;
import com.meti.exception.ParseException;
import com.meti.node.Node;
import com.meti.node.declare.Declarations;
import com.meti.node.declare.DeclareParser;
import com.meti.node.declare.TreeDeclarations;
import com.meti.node.declare.VariableParser;
import com.meti.node.primitive.IntResolver;
import com.meti.node.struct.ReturnParser;
import com.meti.node.struct.StructUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructUnitTest {
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
		assertEquals("int _exitCode=0;int complete(int value){return value;}int main(){return _exitCode;}",
				cache.render());
	}

	@BeforeEach
	void setUp() {
		TreeDeclarations declarations = new Declarations();
		Unit unit = new StructUnit(declarations, cache);
		Parser parser = new ParentParser(
				unit,
				new DeclareParser(declarations),
				new ReturnParser(),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				unit,
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