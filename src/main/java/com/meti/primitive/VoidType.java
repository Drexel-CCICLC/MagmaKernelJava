package com.meti.primitive;

import com.meti.Type;

public class VoidType implements Type {
	public static final Type INSTANCE = new VoidType();

	@Override
	public boolean isNamed() {
        return false;
	}

	@Override
	public String render() {
		return "void";
	}
}
