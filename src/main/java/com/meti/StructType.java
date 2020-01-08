package com.meti;

import java.util.List;
import java.util.Optional;

public interface StructType extends Type {
	List<Type> parameters();

	Optional<Type> returnType();
}
