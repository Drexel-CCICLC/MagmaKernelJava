package com.meti.declare;

import com.meti.Node;

import java.util.LinkedList;

public class AssignNode implements Node {
	private final Node from;
	private final Node to;

	AssignNode(Node to, Node from) {
		this.from = from;
		this.to = to;
	}

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
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
