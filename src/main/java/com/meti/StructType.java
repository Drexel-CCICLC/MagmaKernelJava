package com.meti;

public class StructType implements Type {
	private final String name;

	public StructType(String name) {
		this.name = name;
	}

	@Override
	public String render() {
		return "struct " + name;
	}
}
