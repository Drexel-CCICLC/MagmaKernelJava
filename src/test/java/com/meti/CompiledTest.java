package com.meti;

public class CompiledTest {
	private final Compiler compiler = new UnitCompiler(new ValueUnit());

	protected String compile(String s) {
		return compiler.compile(s).render();
	}
}
