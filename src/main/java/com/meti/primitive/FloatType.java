package com.meti.primitive;

import com.meti.Type;

public class FloatType implements Type {
	@Override
	public boolean isNamed() {
        return false;
	}

	@Override
    public String render() {
        return "float";
    }
}
