package com.meti.integer;

import com.meti.Type;

public class IntType implements Type {
	@Override
	public boolean isNamed() {
		return false;
	}

	@Override
	public String render() {
		return "int";
	}
}
