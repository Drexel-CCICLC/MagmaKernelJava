package com.meti.node.primitive;

import com.meti.node.Type;
import com.meti.node.ValueType;

public class StringType extends ValueType {
	public static final Type INSTANCE = new StringType();

	private StringType() {
	}

	@Override
	public String render() {
		return null;
	}

	@Override
	public String render(String name) {
		return "char* " + name;
	}
}
