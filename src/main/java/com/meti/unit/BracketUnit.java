package com.meti.unit;

public class BracketUnit extends CompoundUnit {
	public BracketUnit() {
		this(
				new IfUnit(),
				new ElseUnit(),
				new BlockUnit()
		);
	}

	public BracketUnit(Unit... units) {
		super(units);
	}
}
