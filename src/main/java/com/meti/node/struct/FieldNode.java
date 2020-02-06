package com.meti.node.struct;

import com.meti.node.Node;
import com.meti.node.declare.Declaration;

public class FieldNode implements Node {
	private final String name;
	private final Node node;

	public FieldNode(Declaration instance, String name) {
		this(instance.toVariable(), name);
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
