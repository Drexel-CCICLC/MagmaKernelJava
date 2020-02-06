package com.meti.node.transform;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;
import com.meti.node.primitive.IntParser;
import com.meti.util.ParentParser;
import com.meti.util.UnitCompiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationParserTest {

	@Test
	void parse() {
		Parser parser = new ParentParser(
				new OperationParser(),
				new IntParser()
		);
		Compiler compiler = new UnitCompiler(parser, null);
		Node node = compiler.parse("5 + 10");
		assertEquals("5+10", node.render());
	}
}