package com.meti.node.struct.type;

import com.meti.node.Type;

public interface FunctionType extends Type {
	Type returnType();

	@Override
	default String toMagmaString() {
		return "";
	}
}
