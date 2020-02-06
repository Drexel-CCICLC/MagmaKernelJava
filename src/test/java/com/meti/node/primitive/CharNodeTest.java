package com.meti.node.primitive;

import com.meti.node.Node;
import com.meti.node.primitive.chars.CharNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharNodeTest {

	@Test
	void render() {
		Node node = new CharNode('a');
		assertEquals("'a'", node.render());
	}
}