package com.meti.node.primitive;

import com.meti.node.Type;

public class FloatType implements Type {

	@Override
    public String render() {
        return "float";
    }

	@Override
	public String render(String name) {
		return null;
	}
}
