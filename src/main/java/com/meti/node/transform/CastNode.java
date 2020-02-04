package com.meti.node.transform;

import com.meti.node.Node;
import com.meti.node.Type;

public class CastNode implements Node {
	private final Type type;
	private final Node value;

	public CastNode(Type type, Node value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String render() {
		return "(" + type.render() + ")" + value.render();
	}
}
