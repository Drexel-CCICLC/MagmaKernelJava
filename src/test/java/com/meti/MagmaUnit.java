package com.meti;

public class MagmaUnit extends CompoundUnit {
	public MagmaUnit(Declarations declarations) {
		this(new EqualsUnit(declarations), new ValueUnit());
	}

	public MagmaUnit(Unit... children) {
		super(children);
	}
}
