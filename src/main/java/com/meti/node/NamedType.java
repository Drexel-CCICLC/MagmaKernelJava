package com.meti.node;

public interface NamedType extends Type {
	@Deprecated
	boolean isNamed();

	String renderWithName(String name);
}
