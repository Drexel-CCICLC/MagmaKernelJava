package com.meti;

import com.meti.unit.*;

import java.util.HashSet;
import java.util.Set;

public class CompileTest {
	private final Aliaser aliaser = new SimpleAliaser();
	private final Set<String> declarations = new HashSet<>();
	private final Data data = new Data(declarations, aliaser);
	protected final Compiler compiler = new UnitCompiler(new CompoundUnit(
			new IfUnit(),
			new BlockUnit(),
			new DeclareUnit(data),
			new OperationUnit(),
			new PrimitiveUnit(),
			new VariableUnit(data)
	));
}
