package com.meti;

import com.meti.node.Node;
import com.meti.node.primitive.strings.StringNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringNodeTest {
	@Test
	void test() {
		Node node = new StringNode("Hello World!");
		assertEquals("\"Hello World!\"", node.render());
	}
}
