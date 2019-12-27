package com.meti.type;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public interface Type {
	Collection<Type> children();

	Optional<Type> parent();

	Optional<Type> returnType();
}
