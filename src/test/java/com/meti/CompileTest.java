package com.meti;

import com.meti.unit.CompoundUnit;
import com.meti.unit.DeclareUnit;
import com.meti.unit.PrimitiveUnit;
import com.meti.unit.UnitCompiler;

import java.util.HashSet;

public class CompileTest {
	protected final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new DeclareUnit(new SimpleCounter(), new HashSet<>()),
			new PrimitiveUnit()
	));
}
