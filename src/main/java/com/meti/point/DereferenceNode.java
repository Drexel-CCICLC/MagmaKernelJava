package com.meti.point;

import com.meti.Node;

public class DereferenceNode implements Node {
	private final Node child;

	public DereferenceNode(Node child) {
		this.child = child;
	}

	@Override
	public String render() {
		return "*" + child.render();
	}
}
