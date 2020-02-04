package com.meti.node.thrower;

import com.meti.node.Node;
import com.meti.node.declare.AssignNode;
import com.meti.node.declare.VariableNode;

public class ThrowNode implements Node {
	private static final Node THROW = new VariableNode("_throw");
	private final Node node;

	public ThrowNode(Node node) {
		this.node = node;
	}

	@Override
	public String render() {
		Node assign = new AssignNode(THROW, node);
		return assign.render();
	}
}
