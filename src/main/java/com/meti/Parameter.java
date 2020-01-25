package com.meti;

public class Parameter {
	private final String name;
	private final Type type;

	public Parameter(Type type, String name) {
		this.type = type;
		this.name = name;
	}

	public String render() {
		return type.render() + " " + name;
	}
}
