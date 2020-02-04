package com.meti.node.point;

import com.meti.node.Type;

public class PointerType implements Type {
	private final Type child;

	public PointerType(Type child) {
		this.child = child;
	}

	@Override
	public boolean isFunctional() {
		return child.isFunctional();
	}

	@Override
	public String render() {
		return null;
	}

	@Override
	public String render(String name) {
		return child.render("*" + name);
	}
}
