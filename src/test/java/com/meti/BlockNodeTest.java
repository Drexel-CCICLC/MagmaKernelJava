package com.meti;

import com.meti.primitive.IntNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockNodeTest {

	@Test
	void render() {
		Node value0 = new IntNode(10);
		Node value1 = new StringNode("test");
		Node node = new BlockNode(List.of(value0, value1));
		assertEquals("{10i\"test\"}", node.render());
	}
}