package com.meti;

import com.meti.node.Node;
import com.meti.node.primitive.IntNode;
import com.meti.node.primitive.StringNode;
import com.meti.node.struct.BlockNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockNodeTest {

	@Test
	void render() {
		Node value0 = new IntNode(10);
		Node value1 = new StringNode("test");
		Node node = new BlockNode(List.of(value0, value1));
		assertEquals("{10\"test\"}", node.render());
	}
}