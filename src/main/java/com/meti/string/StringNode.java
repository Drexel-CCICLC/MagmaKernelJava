package com.meti.string;

import com.meti.Node;

import java.util.LinkedList;

public class StringNode implements Node {
	private final String value;

	public StringNode(String value) {
		this.value = value;
	}

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
	public String render() {
		return "\"" + value + "\"";
	}

    @Override
    public boolean isParent() {
		return false;
    }
}
