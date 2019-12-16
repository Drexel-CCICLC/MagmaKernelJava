package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationNodeTest {

	@Test
	void parse() {
		var node = new OperationNodeFactory()
				.parse("10+20", new FactoryParser(new PrimitiveNodeFactory()))
				.orElseThrow();
		var actual = node.compile(new IncrementAliaser());
		assertEquals("10+20", actual);
	}

	@Test
	void parseVars() {
		var tree = new ListNodeTree();
		var parser = new FactoryParser(
				new BlockNodeFactory(),
				new DeclareNodeFactory(tree),
				new OperationNodeFactory(),
				new PrimitiveNodeFactory(),
				new VariableNodeFactory(tree));
		var node = parser.parse("{val x=10;val y=20;val z=x+y;}");
		var actual = node.compile(new IncrementAliaser());
		assertEquals("{var a0=10;var b1=20;var c2=a0+b1;}", actual);
	}
}