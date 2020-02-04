package com.meti.node;

public interface Type {
	String toMagmaString();

	boolean isFunctional();

	String render();

	String render(String name);
}
