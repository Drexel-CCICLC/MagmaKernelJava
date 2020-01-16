package com.meti.array;

import com.meti.Node;
import com.meti.Type;
import com.meti.invoke.InvocationNode;
import com.meti.other.VoidType;
import com.meti.struct.StructType;
import com.meti.variable.VariableNode;

import java.util.Collections;

public class ArrayDeleteNode implements Node {
	private static final Node FREE_NODE = new VariableNode("free");
	private static final Type FREE_TYPE = new StructType(new VoidType(), "free", Collections.emptyList());
	private final Node array;

	public ArrayDeleteNode(Node array) {
		this.array = array;
	}

	@Override
	public boolean isParent() {
		return false;
	}

	@Override
	public String render() {
		return new InvocationNode(FREE_TYPE, FREE_NODE, Collections.singletonList(array)).render();
	}
}
