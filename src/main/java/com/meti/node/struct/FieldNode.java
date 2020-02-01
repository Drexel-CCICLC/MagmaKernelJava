package com.meti.node.struct;

import com.meti.node.Node;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.VariableNode;

public class FieldNode implements Node {
	private final Declaration instance;
	private final String name;

	public FieldNode(Declaration instance, String name) {
		this.instance = instance;
		this.name = name;
	}

	@Override
	public String render() {
		return new VariableNode(instance.getName()).render() + "." + name;
	}
}
