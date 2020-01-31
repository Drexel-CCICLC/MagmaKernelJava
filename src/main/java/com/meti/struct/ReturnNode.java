package com.meti.struct;

import com.meti.Node;

public class ReturnNode implements Node {
	private final Node value;

	public ReturnNode(Node value) {
		this.value = value;
	}

	@Override
	public String render() {
		return "return " + value.render() + ";";
	}
}
