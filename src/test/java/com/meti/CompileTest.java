package com.meti;

import java.util.List;

public class CompileTest {
	private final StringBuilder callback = new StringBuilder();
	private final Declarations declarations = new Declarations();
	protected final Compiler compiler = new UnitCompiler(List.of(
			new VoidUnit(),
			new EmptyUnit(),
			new CharUnit(),
			new BlockUnit(),
			new OperationUnit(),
			new DeclareUnit(callback, declarations),
			new StructUnit(callback, declarations),
			new IntUnit()));

	protected String compileAll(String value) {
		String result = compiler.compileAll(value);
		return callback.toString() + result;
	}

	protected String compileOnly(String value) {
		String result = compiler.compileOnly(value);
		return callback.toString() + result;
	}
}
