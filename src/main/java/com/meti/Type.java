package com.meti;

import java.util.List;
import java.util.Optional;

public interface Type {
	Optional<Type> child();

	boolean isPointer();

	List<Type> parameters();

	String render();

	Optional<Type> returnType();
}
