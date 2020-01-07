package com.meti;

public interface Compiler {
	String compileAll(String input);

	String compileOnly(String input);

	Type resolveName(String value);

	Type resolveValue(String value);
}
