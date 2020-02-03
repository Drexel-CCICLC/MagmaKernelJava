package com.meti.node;

public interface Type {
	boolean isFunctional();

	@Deprecated
	String render();

	String render(String name);
}
