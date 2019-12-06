package com.meti.compile;

import java.util.Set;

public class MagmaTranslator extends UnitTranslator {
	public MagmaTranslator() {
		this(Set.of(
				new AssignUnit(),
				new DeclareUnit(),
				new PrimitiveUnit(),
                new VariableUnit(),
				new IfUnit(),
				new WhileUnit(),
				new BlockUnit()
        ), new SimpleAliaser());
	}

	private MagmaTranslator(Set<Unit> units, Aliaser aliaser) {
		super(units, aliaser);
	}
}
