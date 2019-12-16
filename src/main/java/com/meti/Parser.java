package com.meti;

public interface Parser {
	Node parse(String value);

	Struct resolve(String value);
}
