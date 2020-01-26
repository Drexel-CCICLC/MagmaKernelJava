package com.meti;

public class Parameter {
	private final String name;
	private final Type type;

	public Parameter(Type type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String render() {
		if (type.isNamed()) {
			return type.render();
		} else {
			return type.render() + " " + name;
		}
	}
}
