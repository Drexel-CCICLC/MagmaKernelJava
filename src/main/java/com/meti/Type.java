package com.meti;

import java.util.Optional;
import java.util.OptionalInt;

public interface Type {
	OptionalInt childOrder(String name);

	Optional<Type> childType(String name);

	boolean isNamed();

	String render();

	Optional<Type> returnType();

	boolean doesReturnVoid();
}
