package com.meti.unit.value;

import com.meti.unit.CompoundUnit;
import com.meti.unit.Unit;

public class ValueUnit extends CompoundUnit {
	public ValueUnit() {
		this(new IntUnit());
	}

	public ValueUnit(Unit... children) {
		super(children);
	}
}
