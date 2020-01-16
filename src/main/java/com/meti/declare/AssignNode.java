package com.meti.declare;

import com.meti.Node;

public class AssignNode implements Node {
	private final Node from;
	private final Node to;

	AssignNode(Node to, Node from) {
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean isParent() {
		return false;
	}

	@Override
	public String render() {
		return to.render() + "=" + from.render() + ";";
	}
}
