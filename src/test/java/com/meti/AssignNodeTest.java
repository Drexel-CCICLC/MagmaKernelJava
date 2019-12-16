package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignNodeTest {

	@Test
	void parse() {
		NodeTree tree = new ListNodeTree();
		Parser parser = new FactoryParser(new PrimitiveNodeFactory(), new VariableNodeFactory(tree));
		new DeclareNodeFactory(tree)
				.parse("val x=10", parser);
		var node = new AssignNodeFactory(tree)
				.parse("x=20", parser)
				.orElseThrow();
		var result = node.compile(new IncrementAliaser());
		assertEquals("a0=20;", result);
	}
}