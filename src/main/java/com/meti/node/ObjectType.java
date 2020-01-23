package com.meti.node;

import java.util.Optional;

public interface ObjectType extends StructType {
	Optional<Type> childType(String childName);

	Optional<Node> toField(Node instance, String name);
}
