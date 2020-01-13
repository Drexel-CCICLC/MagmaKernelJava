package com.meti.point;

import com.meti.Type;

public class PointerType implements Type {
	private final Type child;

	public PointerType(Type child) {
		this.child = child;
	}

	@Override
	public boolean isNamed() {
		return child.isNamed();
	}

	@Override
	public String render() {
		//TODO: rule changes with functions?
		return child.render() + "*";
	}
}
