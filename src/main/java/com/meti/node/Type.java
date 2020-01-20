package com.meti.node;

import java.util.Optional;
import java.util.OptionalInt;

public interface Type {
	OptionalInt childOrder(String childName);

	Optional<Type> childType(String childName);

	boolean isNamed();

	String render();

	String renderWithName(String name);

	Optional<Type> returnType();

	boolean doesReturnVoid();
}
