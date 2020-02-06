package com.meti.node.array;

import com.meti.node.Type;

public class PointerArrayType extends ArrayType {
	public PointerArrayType(Type elementType) {
		super(elementType);
	}

	@Override
	public String render() {
		return elementType.render() + "*";
	}

	@Override
	public String render(String name) {
		//TODO: fix implementation
		/*       return elementType.render() + "* " + name;*/
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}
