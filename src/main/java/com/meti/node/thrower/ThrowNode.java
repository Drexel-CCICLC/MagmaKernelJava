package com.meti.node.thrower;

import com.meti.node.Node;
import com.meti.node.declare.AssignNode;
import com.meti.node.declare.VariableNode;
import com.meti.node.point.ReferenceNode;

public class ThrowNode implements Node {
	private static final Node THROW = new VariableNode("_throw");
	private final Node node;

	public ThrowNode(Node node) {
		this.node = node;
	}

	@Override
	public String render() {
		Node reference = new ReferenceNode(node);
		Node assign = new AssignNode(THROW, reference);
		return assign.render();
	}
}
