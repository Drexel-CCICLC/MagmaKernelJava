package com.meti.node.struct.type;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.parse.Declaration;

import java.util.Optional;

public interface StructType extends Type {
	Node bind(String instanceName, String child);

	Declaration declaration();

	Optional<Type> typeOf(String child);
}
