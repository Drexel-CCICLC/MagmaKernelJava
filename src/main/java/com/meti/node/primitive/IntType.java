package com.meti.node.primitive;

import com.meti.node.Type;

public class IntType implements Type {
	public static final Type INSTANCE = new IntType();

	@Override
	public boolean isFunctional() {
		return false;
	}

	@Override
	public String render() {
		return "int";
	}

	@Override
	public String render(String name) {
		return "int " + name;
	}
}
