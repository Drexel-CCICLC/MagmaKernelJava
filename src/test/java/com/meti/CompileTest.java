package com.meti;

import java.util.List;

public class CompileTest {
	private final Declarations declarations = new Declarations();
	protected final Compiler compiler = new UnitCompiler(List.of(
			new VoidUnit(),
			new EmptyUnit(),
			new CharUnit(),
			new BlockUnit(),
			new OperationUnit(),
			new DeclareUnit(declarations),
			new StructUnit(declarations),
			new IntUnit()));

	protected String compileAll(String value) {
		return compiler.compileAll(value);
	}

	protected String compileOnly(String value) {
		return compiler.compileOnly(value);
	}
}
