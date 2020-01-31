package com.meti;

import com.meti.node.Node;
import com.meti.node.declare.VariableNode;
import com.meti.node.struct.InvocationNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvocationNodeTest {

	@Test
	void render() {
		Node a = new VariableNode("a");
		Node b = new VariableNode("b");
		Node node = new InvocationNode(a, Collections.singletonList(b));
		assertEquals("a(b)", node.render());
	}
}