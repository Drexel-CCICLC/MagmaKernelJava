package com.meti.transform;

import com.meti.Node;

public class QuantityNode implements Node {
	private final Node value;

	public QuantityNode(Node value) {
		this.value = value;
	}

	@Override
	public String render() {
        return "(" + value.render() + ")";
    }
}
