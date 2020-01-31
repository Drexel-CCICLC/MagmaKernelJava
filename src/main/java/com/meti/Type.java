package com.meti;

import java.util.Optional;
import java.util.OptionalInt;

//TODO: fix problems with optionals
//if there are so many optionals, then that means there must be higher abstractions
public interface Type {
	OptionalInt childOrder(String name);

	Optional<Type> childType(String name);

	boolean isNamed();

	String render();

	Optional<Type> returnType();
}
