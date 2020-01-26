package com.meti;

import java.util.List;
import java.util.Optional;

public interface Declaration {
	Optional<Declaration> child(String name);

	List<Declaration> children();

	Declaration define(Type type, String name);

	Declaration define(Parameter parameter);

	String getName();

	Type getType();

	boolean isParent();
}
