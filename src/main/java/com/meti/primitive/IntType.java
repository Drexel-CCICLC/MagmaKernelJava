package com.meti.primitive;

import com.meti.Type;

public class IntType implements Type {
    public static final Type INSTANCE = new IntType();

	@Override
    public String render() {
        return "int";
    }
}
