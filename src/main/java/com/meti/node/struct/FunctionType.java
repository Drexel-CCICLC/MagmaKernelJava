package com.meti.node.struct;

import com.meti.node.Type;

public interface FunctionType extends Type {
	Type returnType();

	@Override
	default String toMagmaString() {
		return "";
	}
}
