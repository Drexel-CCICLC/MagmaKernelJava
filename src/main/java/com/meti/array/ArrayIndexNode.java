package com.meti.array;

import com.meti.Node;

public class ArrayIndexNode implements Node {
	private final Node array;
	private final Node index;

	public ArrayIndexNode(Node array, Node index) {
		this.array = array;
		this.index = index;
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
