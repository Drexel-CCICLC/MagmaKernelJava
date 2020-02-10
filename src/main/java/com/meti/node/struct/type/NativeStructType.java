package com.meti.node.struct.type;

import com.meti.node.ValueType;

public class NativeStructType extends ValueType {
	private final String name;

	public NativeStructType(String name) {
		this.name = name;
	}

	@Override
	public String toMagmaString() {
		return "";
	}

	@Override
	public String render() {
		return "struct " + name;
	}

	@Override
	public String render(String name) {
		return "struct " + this.name + " " + name;
	}
}
