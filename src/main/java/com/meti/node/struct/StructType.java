package com.meti.node.struct;

import com.meti.node.Type;

public class StructType implements Type {
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
