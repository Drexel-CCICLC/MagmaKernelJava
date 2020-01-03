package com.meti.unit.equals;

import com.meti.unit.CompoundUnit;
import com.meti.unit.Unit;
import com.meti.unit.equals.declare.Declarations;
import com.meti.unit.equals.declare.DeclareUnit;

public class EqualsUnit extends CompoundUnit {
	public EqualsUnit(Declarations declarations) {
		this(new DeclareUnit(declarations));
	}

	public EqualsUnit(Unit... children) {
		super(children);
	}
}
