package com.meti.node.value.primitive.array;

import com.meti.node.Node;

import java.util.LinkedList;

public class ArrayIndexNode implements Node {
	private final Node array;
	private final Node index;

	public ArrayIndexNode(Node array, Node index) {
		this.array = array;
		this.index = index;
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
		return array.render() + "[" + index.render() + "]";
	}
}
