package com.meti;

public interface Parser {
	Node parse(String value);

	Node parse(String value, Node parent);

	Struct resolve(String value);
}
