package com.meti.declare;

import com.meti.node.Type;

import java.util.Map;
import java.util.Optional;

public interface Declaration {
	Optional<Declaration> child(String name);

	Map<String, Type> childMap();

	Declaration define(DeclarationBuilder builder);

	boolean hasParameter(String childName);

	boolean isParameter();

	String name();

	Optional<Declaration> parent();

	Type type();

	boolean isNamedAs(String name);
}
