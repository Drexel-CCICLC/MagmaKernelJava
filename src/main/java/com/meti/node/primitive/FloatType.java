package com.meti.node.primitive;

import com.meti.node.array.ValueType;

public class FloatType extends ValueType {
	@Override
	public String render() {
		return "float";
	}

	@Override
	public String render(String name) {
		return "float " + name;
	}
}
