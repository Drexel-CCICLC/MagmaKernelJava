package com.meti.node;

public interface DefaultType extends Type {
	Node defaultValue();

	@Override
	default String toMagmaString() {
		return "";
	}
}
