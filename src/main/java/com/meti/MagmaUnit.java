package com.meti;

public class MagmaUnit extends CompoundUnit {
	public MagmaUnit() {
		this(new ValueUnit());
	}

	public MagmaUnit(Unit... units) {
		super(units);
	}
}
