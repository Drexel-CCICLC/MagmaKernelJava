package com.meti.node.point;

import com.meti.node.ValueType;

public class AnyType extends ValueType {
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
