package com.meti.node;

import java.util.Optional;

public interface Type {
	Optional<Type> childType(String childName);

	boolean doesReturnVoid();

	boolean isNamed();

	Optional<String> name();

	String render();

	String renderWithName(String name);

	Optional<Type> returnType();

	Optional<Node> toField(Node instance, String name);
}
