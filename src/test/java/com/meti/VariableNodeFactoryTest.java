package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VariableNodeFactoryTest {

	@Test
	void parse() {
		NodeTree tree = new ListNodeTree();
		new DeclareNodeFactory(tree).parse("val x=10", new FactoryParser(new PrimitiveNodeFactory()));
		NamedNode node = (NamedNode) new VariableNodeFactory(tree)
				.parse("x", null)
				.orElseThrow();
		assertEquals("x", node.name());
		assertEquals("a0", node.compile(new IncrementAliaser()));
	}
}