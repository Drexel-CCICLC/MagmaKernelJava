package com.meti;

import com.meti.unit.*;
import com.meti.unit.bracket.BracketUnit;
import com.meti.unit.value.ValueUnit;

public class CompileTest {
	private final Data data = new SimpleData();
	protected final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new BracketUnit(data),
			new ReturnUnit(),
			new DeclareUnit(data),
			new ValueUnit(data)
	));
}
