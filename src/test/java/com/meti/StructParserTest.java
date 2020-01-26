package com.meti;

import com.meti.exception.ParseException;
import com.meti.primitive.IntResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructParserTest {

	@Test
	void parseComplete() throws ParseException {
		Declarations declarations = new Declarations();
		Cache cache = new Cache();
		Parser parser = new ParentParser(
				new StructParser(declarations, cache),
				new DeclareParser(declarations),
				new ReturnParser(),
				new VariableParser(declarations)
		);
		Resolver resolver = new ParentResolver(
				new StructResolver(),
				new IntResolver()
		);
		Compiler compiler = new Compiler(parser, resolver);
		Node node = compiler.parse("""
				            val complete = (Int value) => Int :{
				                return value;
				            }
				""");
		assertTrue(node.render().isBlank());
		assertEquals("int _exit=0i;int complete(int value){return value;}int main(){return _exit;}", cache.render());
	}
}