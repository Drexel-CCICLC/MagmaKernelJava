package com.meti.node.primitive.doubles;

import com.meti.node.Type;
import com.meti.node.ValueType;

public class DoubleType extends ValueType {
	public static final Type INSTANCE = new DoubleType();

	@Override
	public String toMagmaString() {
		return "Double";
	}

	@Override
	public String render() {
		return "double";
	}

	@Override
	public String render(String name) {
		return "double " + name;
	}
}
