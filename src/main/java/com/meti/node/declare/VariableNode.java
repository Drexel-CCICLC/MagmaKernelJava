package com.meti.node.declare;

import com.meti.node.Node;

public class VariableNode implements Node {
	private final String value;

	public VariableNode(String value) {
		this.value = value;
	}

	@Override
	public String render() {
		return value;
	}
}
