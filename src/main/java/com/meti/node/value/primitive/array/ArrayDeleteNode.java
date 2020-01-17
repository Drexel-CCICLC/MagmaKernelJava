package com.meti.node.value.primitive.array;

import com.meti.node.Node;
import com.meti.node.value.compound.invoke.InvocationNode;
import com.meti.node.value.compound.variable.VariableNode;

import java.util.Collections;
import java.util.LinkedList;

public class ArrayDeleteNode implements Node {
	private static final Node FREE_NODE = new VariableNode("free");
	private final Node array;

	ArrayDeleteNode(Node array) {
		this.array = array;
	}

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
	public boolean isParent() {
		return false;
	}

	@Override
	public String render() {
		return new InvocationNode(FREE_NODE, Collections.singletonList(array), true).render();
	}
}
