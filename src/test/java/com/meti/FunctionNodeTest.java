package com.meti;

import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.primitive.ints.IntType;
import com.meti.node.primitive.special.VoidType;
import com.meti.node.struct.BlockNode;
import com.meti.node.struct.FunctionNode;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionNodeTest {

	@Test
	void render() {
		Set<Parameter> params = Collections.singleton(Parameter.create(IntType.INSTANCE, Collections.singletonList(
				"value")));
		Node block = new BlockNode(Collections.emptyList());
		Node node = new FunctionNode("doSomething", VoidType.INSTANCE, block, params);
		assertEquals("void doSomething(int value){}", node.render());
	}
}