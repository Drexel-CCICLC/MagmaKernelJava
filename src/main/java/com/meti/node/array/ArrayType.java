package com.meti.node.array;

import com.meti.node.Type;

public abstract class ArrayType extends ValueType {
	protected final Type elementType;

	public ArrayType(Type elementType) {
		this.elementType = elementType;
	}

}
