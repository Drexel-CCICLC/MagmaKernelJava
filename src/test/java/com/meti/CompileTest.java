package com.meti;

import com.meti.unit.*;

import java.util.HashSet;
import java.util.Set;

public class CompileTest {
	private final Set<String> declarations = new HashSet<>();
	private final Aliaser aliaser = new SimpleAliaser();
	protected final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new DeclareUnit(aliaser, declarations),
			new PrimitiveUnit(),
			new VariableUnit(declarations, aliaser)
	));
}
