package com.meti;

public class NumberUnit extends CompoundUnit {
	public NumberUnit() {
		this(new IntUnit());
	}

	public NumberUnit(Unit... units) {
		super(units);
	}
}
