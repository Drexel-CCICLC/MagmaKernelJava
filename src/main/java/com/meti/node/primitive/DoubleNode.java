package com.meti.node.primitive;

import com.meti.node.Node;

public class DoubleNode implements Node {
	private final double value;

	public DoubleNode(double value) {
		this.value = value;
	}

	@Override
	public String render() {
		return String.valueOf(value);
	}
}
