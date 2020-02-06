package com.meti;

import com.meti.node.Node;
import com.meti.node.primitive.ints.IntNode;
import com.meti.node.transform.QuantityNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuantityNodeTest {

	@Test
	void render() {
		Node value = new IntNode(10);
		Node node = new QuantityNode(value);
		assertEquals("(10)", node.render());
	}
}