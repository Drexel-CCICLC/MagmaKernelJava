package com.meti.node;

import java.util.Optional;

public interface Type {
    Optional<Type> childType(String childName);

	Optional<Node> toField(Node instance, String name);

    boolean doesReturnVoid();

    boolean isNamed();

    String render();

    String renderWithName(String name);

    Optional<Type> returnType();
}
