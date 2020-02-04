package com.meti.node.transform;

import com.meti.node.Node;

public class NotNode implements Node {
	private final Node value;

	public NotNode(Node value) {
		this.value = value;
	}

	@Override
	public String render() {
		return "!" + value.render();
	}
}
