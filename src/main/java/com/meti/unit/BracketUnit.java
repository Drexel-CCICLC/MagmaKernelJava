package com.meti.unit;

public class BracketUnit extends CompoundUnit {
	public BracketUnit(Data data) {
		this(
				new StructUnit(data),
				new IfUnit(),
				new ElseUnit(),
				new BlockUnit()
		);
	}

	public BracketUnit(Unit... units) {
		super(units);
	}
}
