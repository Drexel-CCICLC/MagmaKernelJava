package com.meti;

import com.meti.primitive.IntType;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StructNodeTest {

	@Test
	void render() {
		Parameter x = new Parameter(IntType.INSTANCE, "x");
		Parameter y = new Parameter(IntType.INSTANCE, "y");
		Collection<Parameter> parameters = List.of(x, y);
		Node node = new StructNode("Point", parameters);
		assertEquals("struct Point{int x;int y;};", node.render());
	}
}