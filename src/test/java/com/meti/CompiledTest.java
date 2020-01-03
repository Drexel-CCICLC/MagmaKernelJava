package com.meti;

import com.meti.unit.UnitCompiler;
import com.meti.unit.value.ValueUnit;

public class CompiledTest {
	private final Compiler compiler = new UnitCompiler(new ValueUnit());

	protected String compile(String s) {
		return compiler.compile(s).render();
	}
}
