package com.meti;

public class CompileTest {
	protected final Compiler compiler = new Compiler();

	protected String compile(String value) {
		return compiler.compileFull(value);
	}
}
