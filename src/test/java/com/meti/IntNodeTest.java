package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.meti.PrimitiveNodeFactory.PrimitiveStruct.INT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntNodeTest {
	private final NodeFactory factory = new PrimitiveNodeFactory();

	@Test
	void compile() {
		Node node = factory.parse("10", null)
				.orElseThrow();
		assertEquals("10", node.compile());
	}

	@Test
	void parse() {
		Optional<Node> optional = factory.parse("10", null);
		assertTrue(optional.isPresent());
		Node node = optional.get();
		assertEquals(INT, node.struct());
		assertEquals(10, node.value());
	}

	@Test
	void transform() {
		Node expected = factory.parse("10", null)
				.orElseThrow();
		Node actual = expected.transform();
		assertEquals(expected, actual);
	}
}