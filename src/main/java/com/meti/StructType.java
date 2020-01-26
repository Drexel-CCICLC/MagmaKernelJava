package com.meti;

public class StructType implements Type {
	private final String name;

	public StructType(String name) {
		this.name = name;
	}

	@Override
	public boolean isNamed() {
        return false;
	}

	@Override
	public String render() {
		return "struct " + name;
	}
}
