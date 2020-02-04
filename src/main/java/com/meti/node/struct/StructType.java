package com.meti.node.struct;

import com.meti.node.ValueType;

public class StructType extends ValueType {
	private final String name;

	public StructType(String name) {
		this.name = name;
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
