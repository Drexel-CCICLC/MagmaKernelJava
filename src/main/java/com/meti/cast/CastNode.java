package com.meti.cast;

import com.meti.Node;
import com.meti.Type;

import java.util.LinkedList;

public class CastNode implements Node {
	private final Type type;
	private final Node value;

	public CastNode(Type type, Node value) {
		this.type = type;
		this.value = value;
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
		return "(" + type.render() + ")" + value.render();
	}
}
