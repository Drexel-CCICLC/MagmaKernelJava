package com.meti;

import com.meti.unit.*;

public class CompileTest {
	private final Data data = new Data();
	protected final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new BracketUnit(data),
			new ReturnUnit(),
			new DeclareUnit(data),
			new ValueUnit(data)
	));
}
