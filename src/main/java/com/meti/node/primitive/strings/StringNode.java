package com.meti.node.primitive.strings;

import com.meti.node.Node;

public class StringNode implements Node {
	private final String value;

	public StringNode(String value) {
		this.value = value;
	}

	@Override
	public String render() {
		return "\"" + value + "\"";
	}
}
