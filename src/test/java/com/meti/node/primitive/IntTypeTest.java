package com.meti.node.primitive;

import com.meti.node.Type;
import com.meti.node.primitive.ints.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntTypeTest {

	@Test
	void render() {
		Type type = new IntType();
		assertEquals("int test", type.render("test"));
	}
}