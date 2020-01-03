package com.meti;

public interface Compiler {
	Node compile(String value);

	Type resolve(String value);
}
