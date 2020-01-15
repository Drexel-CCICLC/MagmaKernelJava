package com.meti;

import java.util.Optional;

public interface Type {
	Optional<Type> returnType();

	boolean isNamed();

	String render();
}
