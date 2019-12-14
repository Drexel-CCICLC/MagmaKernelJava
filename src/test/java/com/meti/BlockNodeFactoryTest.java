package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockNodeFactoryTest {

	@Test
	void parse() {
		NodeTree tree = new ListNodeTree();
		Parser parser = new FactoryParser(
				new DeclareNodeFactory(tree),
				new AssignNodeFactory(tree),
				new PrimitiveNodeFactory(),
				new VariableNodeFactory(tree)
		);
		var result = new BlockNodeFactory()
				.parse("{val x=10;x=20;}", parser)
				.orElseThrow()
				.compile(new IncrementAliaser());
		assertEquals("{var a0=10;a0=20;}", result);
	}
}