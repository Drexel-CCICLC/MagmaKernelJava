package com.meti;

import java.util.Map;
import java.util.Optional;

public interface Declaration {
	Optional<Declaration> child(String name);

	Map<String, Type> childMap();

	void define(String name, Type type, boolean isParameter);

	boolean hasChildAsParameter(String childName);

	boolean isParameter();

	String name();

	Optional<Declaration> parent();

	Type type();
}
