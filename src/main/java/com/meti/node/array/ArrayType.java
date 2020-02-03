package com.meti.node.array;

import com.meti.node.Type;

abstract class ArrayType extends ValueType {
	final Type elementType;

	ArrayType(Type elementType) {
		this.elementType = elementType;
	}

}
