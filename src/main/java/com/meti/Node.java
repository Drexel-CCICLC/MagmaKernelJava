package com.meti;

public interface Node {
	String compile();

	Struct struct();

	Object value();

	Node transform();
}
