package com.meti.node.struct;

import com.meti.node.Node;

public class FieldNode implements Node {
	private final Node instance;
	private final String name;

	public FieldNode(Node instance, String name) {
		this.instance = instance;
		this.name = name;
	}

	@Override
	public String render() {
		return instance.render() + "." + name;
	}
}
