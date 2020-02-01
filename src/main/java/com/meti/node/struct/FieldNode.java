package com.meti.node.struct;

import com.meti.node.Node;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.VariableNode;

public class FieldNode implements Node {
	private final String name;
	private final Node node;

	public FieldNode(Declaration instance, String name) {
		this(new VariableNode(instance.getName()), name);
	}

	public FieldNode(Node node, String name) {
		this.name = name;
		this.node = node;
	}

	@Override
	public String render() {
		return node.render() + "." + name;
	}
}
