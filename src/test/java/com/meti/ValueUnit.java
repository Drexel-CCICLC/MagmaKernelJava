package com.meti;

public class ValueUnit extends CompoundUnit {
	public ValueUnit() {
		this(new IntUnit());
	}

	public ValueUnit(Unit... children) {
		super(children);
	}
}
