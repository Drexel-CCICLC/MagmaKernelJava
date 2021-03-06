package com.meti.node.array.type;

import com.meti.node.Type;

public class IndexedArrayType extends ArrayType {
	public IndexedArrayType(Type elementType) {
		super(elementType);
	}

	@Override
	public String render(String name) {
		return elementType.render(name) + "[]";
	}
}
