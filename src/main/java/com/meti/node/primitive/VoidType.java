package com.meti.node.primitive;

import com.meti.node.Type;

public class VoidType implements Type {
	public static final Type INSTANCE = new VoidType();

	@Override
	public boolean isFunctional() {
		return false;
	}

	@Override
	public String render() {
		return "void";
	}

	@Override
	public String render(String name) {
		return "void " + name;
	}
}
