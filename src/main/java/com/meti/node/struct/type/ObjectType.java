package com.meti.node.struct.type;

import com.meti.node.Type;
import com.meti.parse.Declaration;

public interface ObjectType extends Type {
	Declaration declaration();

	@Override
	default String toMagmaString() {
		return "";
	}
}
