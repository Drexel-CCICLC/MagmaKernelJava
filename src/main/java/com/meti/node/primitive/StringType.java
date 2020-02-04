package com.meti.node.primitive;

import com.meti.node.DefaultType;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.ValueType;

public class StringType extends ValueType implements DefaultType {
	public static final Type INSTANCE = new StringType();
	private static final String DEFAULT_VALUE = "";

	private StringType() {
	}

	@Override
	public Node defaultValue() {
		return new StringNode(DEFAULT_VALUE);
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
