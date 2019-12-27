package com.meti.unit;

import com.meti.unit.bracket.BracketUnit;
import com.meti.unit.value.NewUnit;
import com.meti.unit.value.ValueUnit;

public class MagmaUnit extends CompoundUnit {
	public MagmaUnit() {
		this(new SimpleData());
	}

	public MagmaUnit(Data data) {
		this(new BracketUnit(data),
				new ReturnUnit(data),
				new DeclareUnit(data),
				new AssignUnit(data),
				new NewUnit(data),
				new ValueUnit(data));
	}

	public MagmaUnit(Unit... units) {
		super(units);
	}
}
