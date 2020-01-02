package com.meti;

public class ValueUnit extends CompoundUnit {
	public ValueUnit() {
		this(new NumberUnit());
	}

	public ValueUnit(Unit... units) {
		super(units);
	}
}
