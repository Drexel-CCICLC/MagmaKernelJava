package com.meti.node.point;

import com.meti.node.DefaultType;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.primitive.special.NullNode;

public class PointerType implements DefaultType {
	private final Type child;

	public PointerType(Type child) {
		this.child = child;
	}

	@Override
	public Node defaultValue() {
		return NullNode.INSTANCE;
	}

	@Override
	public boolean isFunctional() {
		return child.isFunctional();
	}

	@Override
	public String render() {
		return child.render() + "*";
	}

	@Override
	public String render(String name) {
		return child.render("*" + name);
	}
}
