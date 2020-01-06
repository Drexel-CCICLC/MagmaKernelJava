package com.meti;

import java.util.List;

public class CompileTest {
	protected final Compiler compiler = new UnitCompiler(List.of(
			new EmptyUnit(),
			new CharUnit(),
			new BlockUnit(),
			new OperationUnit(),
			new DeclareUnit(),
			new IntUnit()));

	protected String compileAll(String value) {
		return compiler.compileAll(value);
	}

	protected String compileOnly(String value) {
		return compiler.compileOnly(value);
	}
}
