package com.meti;

import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.primitive.IntType;
import com.meti.node.struct.StructNode;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StructNodeTest {

	@Test
	void render() {
		Parameter x = Parameter.create(IntType.INSTANCE, "x");
		Parameter y = Parameter.create(IntType.INSTANCE, "y");
		Collection<Parameter> parameters = List.of(x, y);
		Node node = new StructNode("Point", parameters);
		assertEquals("struct Point{int x;int y;};", node.render());
	}
}