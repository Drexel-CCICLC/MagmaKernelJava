package com.meti.point;

import com.meti.Node;

public class ReferenceNode implements Node {
	private final Node child;

	public ReferenceNode(Node child) {
		this.child = child;
	}

	@Override
	public String render() {
		return "&" + child.render();
	}
}
