package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IfNodeFactoryTest {

	@Test
	void parse() {
		NodeTree tree = new ListNodeTree();
		var node = new IfNodeFactory()
				.parse("if(true){val x=10;}", new FactoryParser(
						new BlockNodeFactory(),
						new DeclareNodeFactory(tree),
						new PrimitiveNodeFactory()
				));
		assertEquals("if(true){var a0=10;}", node.orElseThrow().compile(new IncrementAliaser()));
	}
}