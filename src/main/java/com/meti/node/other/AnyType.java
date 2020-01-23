package com.meti.node.other;

import com.meti.node.Type;

public class AnyType implements Type {
	public static final Type INSTANCE = new AnyType();

	@Override
	public String render() {
		return "...";
    }
}
