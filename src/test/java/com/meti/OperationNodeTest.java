package com.meti;

import com.meti.node.Node;
import com.meti.node.primitive.ints.IntNode;
import com.meti.node.transform.OperationNode;
import com.meti.node.transform.Operations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationNodeTest {

	@Test
	void render() {
		Node value0 = new IntNode(10);
		Node value1 = new IntNode(20);
		Node node = new OperationNode(value0, value1, Operations.ADD);
		assertEquals("10+20", node.render());
	}
}