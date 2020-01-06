package com.meti;

public interface Compiler {
	String compileAll(String input);

	String compileOnly(String input);

	String resolveValue(String value);
}
