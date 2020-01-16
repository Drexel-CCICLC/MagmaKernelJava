package com.meti.point;

import com.meti.Node;

import java.util.LinkedList;

public class DereferenceNode implements Node {
	private final Node child;

	public DereferenceNode(Node child) {
		this.child = child;
	}

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
	public String render() {
		return "*" + child.render();
	}

    @Override
    public boolean isParent() {
		return false;
    }
}
