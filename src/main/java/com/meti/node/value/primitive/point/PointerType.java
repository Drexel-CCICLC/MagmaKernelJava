package com.meti.node.value.primitive.point;

import com.meti.node.NamedType;
import com.meti.node.ObjectType;
import com.meti.node.Type;
import com.meti.node.other.AnyType;

public class PointerType implements NamedType {
	private final Type child;

	private PointerType(Type child) {
		this.child = child;
	}

	public static Type pointerOf(Type child) {
		return new PointerType(child);
	}

	@Override
	public boolean isNamed() {
		return child instanceof ObjectType && ((NamedType) child).isNamed();
	}

	@Override
	public String render() {
		return child.equals(AnyType.INSTANCE) ? "void*" : child.render() + "*";
	}

	@Override
	public String renderWithName(String name) {
		return (isNamed()) ? render() : render() + " " + name;
	}
}
