package com.meti.node.primitive;

import com.meti.node.Type;
import com.meti.node.array.ValueType;

public class VoidType extends ValueType {
	public static final Type INSTANCE = new VoidType();

	@Override
	public String render() {
		return "void";
	}

	@Override
	public String render(String name) {
		return "void " + name;
	}
}
