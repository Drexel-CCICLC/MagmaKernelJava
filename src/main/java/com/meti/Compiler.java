package com.meti;

public interface Compiler {
	Node parse(String value);

	Type resolveName(String name);

	Type resolveValue(String value);
}
