package com.meti.node.primitive.floats;

import com.meti.node.Type;
import com.meti.node.ValueType;

public class FloatType extends ValueType {
	public static final Type INSTANCE = new FloatType();

	@Override
	public String render() {
		return "float";
	}

	@Override
	public String render(String name) {
		return "float " + name;
	}

	@Override
	public String toMagmaString() {
		return "";
	}
}
