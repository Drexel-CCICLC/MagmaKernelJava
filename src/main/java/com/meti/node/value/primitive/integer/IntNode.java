package com.meti.node.value.primitive.integer;

import com.meti.node.Node;

import java.util.LinkedList;

public class IntNode implements Node {
	private final int value;

	public IntNode(int value) {
		this.value = value;
	}

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
	public String render() {
		return String.valueOf(value);
	}

    @Override
    public boolean isParent() {
		return false;
    }
}
