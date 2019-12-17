package com.meti.unit;

import com.meti.Aliaser;

public class BracketUnit extends CompoundUnit {
	public BracketUnit(Aliaser aliaser) {
		this(
				new StructUnit(aliaser),
				new IfUnit(),
				new ElseUnit(),
				new BlockUnit()
		);
	}

	public BracketUnit(Unit... units) {
		super(units);
	}
}
