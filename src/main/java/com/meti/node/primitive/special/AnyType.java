package com.meti.node.primitive.special;

import com.meti.node.Type;
import com.meti.node.ValueType;

public class AnyType extends ValueType {
	public static final Type INSTANCE = new AnyType();

	@Override
	public boolean isFunctional() {
		return false;
	}

	@Override
	public String render() {
		return "void";
	}

	@Override
	public String render(String name) {
		return "void " + name;
	}

	@Override
	public String toMagmaString() {
		return "";
	}
}
