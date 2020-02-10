package com.meti.node.primitive.special;

import com.meti.node.Node;

public class NullNode implements Node {
	public static final Node INSTANCE = new NullNode();

	@Override
	public String render() {
		return "NULL";
	}
}
