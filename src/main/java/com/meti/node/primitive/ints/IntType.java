package com.meti.node.primitive.ints;

import com.meti.node.Type;
import com.meti.node.ValueType;

public class IntType extends ValueType {
	public static final Type INSTANCE = new IntType();

	@Override
	public String render() {
		return "int";
	}

	@Override
	public String render(String name) {
		return "int " + name;
	}

	@Override
	public String toMagmaString() {
		return "";
	}
}
