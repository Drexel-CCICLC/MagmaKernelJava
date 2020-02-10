package com.meti.node.primitive.floats;

import com.meti.node.Node;

public class FloatNode implements Node {
	private final float value;

	public FloatNode(float value) {
		this.value = value;
	}

	@Override
	public String render() {
		return String.valueOf(value);
	}
}
