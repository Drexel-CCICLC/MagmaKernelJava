package com.meti;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Declarations {
	Declaration current();

	Node define(String name, Type type, Supplier<? extends Node> action);

	void define(String name, Type type, boolean isParameter);

	Declaration getRoot();

	Optional<Declaration> relative(String value);

	Stream<Declaration> stackStream();
}
