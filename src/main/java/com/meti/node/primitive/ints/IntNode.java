package com.meti.node.primitive.ints;

import com.meti.node.Node;

public class IntNode implements Node {
	private final int value;

	public IntNode(int value) {
		this.value = value;
	}

	@Override
	public String render() {
		return String.valueOf(value);
	}
}
