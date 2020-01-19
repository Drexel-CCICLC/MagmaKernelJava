package com.meti.declare;

import com.meti.node.Type;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public interface Declaration {
	Optional<Declaration> child(String name);

	Map<String, Type> childMap();

	OptionalInt childOrder(String name);

	Type childType(String childType);

	void define(String name, Type type, boolean isParameter);

	boolean hasChildAsParameter(String childName);

	boolean isParameter();

	String name();

	Optional<Declaration> parent();

	Type type();
}
