package com.meti;

public interface Compiler {
	Node compile(String value);

	Type resolveName(String name);

	Type resolveValue(String value);
}
