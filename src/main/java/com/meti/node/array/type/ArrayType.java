package com.meti.node.array.type;

import com.meti.node.Type;
import com.meti.node.ValueType;

abstract class ArrayType extends ValueType {
	final Type elementType;

	ArrayType(Type elementType) {
		this.elementType = elementType;
	}

	@Override
	public String toMagmaString() {
		return "";
	}

	@Override
	public String render() {
		return elementType.render() + "*";
	}
}
