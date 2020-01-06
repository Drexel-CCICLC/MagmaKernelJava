package com.meti;

public class CompileTest {
	protected final Compiler compiler = new Compiler();

	protected String compileOnly(String value){
		return compiler.compileOnly(value);
	}

	protected String compileAll(String value) {
		return compiler.compileAll(value);
	}
}
