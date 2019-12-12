package com.meti;

public interface Node {
	String compile(Aliaser aliaser);

	Struct struct();

	Object value();

	Node transform();
}
