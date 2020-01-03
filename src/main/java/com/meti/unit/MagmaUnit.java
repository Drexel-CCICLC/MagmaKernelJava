package com.meti.unit;

import com.meti.unit.equals.EqualsUnit;
import com.meti.unit.equals.declare.Declarations;
import com.meti.unit.value.ValueUnit;

public class MagmaUnit extends CompoundUnit {
	public MagmaUnit(Declarations declarations) {
		this(new EqualsUnit(declarations), new ValueUnit());
	}

	public MagmaUnit(Unit... children) {
		super(children);
	}
}
