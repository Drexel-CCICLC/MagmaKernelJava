package com.meti.type;

import java.util.Optional;

public interface Type {
	Optional<Type> parent();

	Optional<Type> returnType();
}
