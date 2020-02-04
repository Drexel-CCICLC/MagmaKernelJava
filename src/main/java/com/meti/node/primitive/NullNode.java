package com.meti.node.primitive;

import com.meti.node.Node;

public class NullNode implements Node {
	@Override
	public String render() {
		return "NULL";
	}
}
