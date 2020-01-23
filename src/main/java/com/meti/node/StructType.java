package com.meti.node;

import java.util.Optional;

public interface StructType extends NamedType {
	Optional<Type> returnType();
}
