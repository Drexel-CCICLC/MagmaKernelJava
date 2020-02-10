package com.meti.node.primitive;

import com.meti.node.Type;
import com.meti.node.primitive.floats.FloatType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatTypeTest {

	@Test
	void render() {
		Type type = new FloatType();
		assertEquals("float test", type.render("test"));
	}
}