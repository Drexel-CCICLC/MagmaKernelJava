package com.meti;

import com.meti.primitive.IntType;
import com.meti.primitive.VoidType;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionNodeTest {

	@Test
	void render() {
		Set<Parameter> params = Collections.singleton(new Parameter(IntType.INSTANCE, "value"));
		Node block = new BlockNode(Collections.emptyList());
		Node node = new FunctionNode("doSomething", VoidType.INSTANCE, params, block);
		assertEquals("void doSomething(int value){}", node.render());
	}
}