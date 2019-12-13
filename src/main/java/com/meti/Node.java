package com.meti;

public interface Node {
	String compile(Aliaser aliaser);

	Struct struct();

	Node transform();
}
