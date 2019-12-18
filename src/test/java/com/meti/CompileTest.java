package com.meti;

import com.meti.unit.*;

public class CompileTest {
	private final Aliaser aliaser = new SimpleAliaser();
	private final Data data = new Data(aliaser, new Declarations());
	protected final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new BracketUnit(aliaser),
			new ReturnUnit(),
			new DeclareUnit(data),
			new ValueUnit(data)
	));
}
