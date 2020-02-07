package com.meti.node.struct.type;

import com.meti.node.Type;
import com.meti.parse.Declaration;

import java.util.Optional;

public interface ObjectType extends Type {
	Optional<Type> childType(String child);

	Declaration declaration();
}
