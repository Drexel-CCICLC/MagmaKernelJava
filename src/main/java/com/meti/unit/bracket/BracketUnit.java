package com.meti.unit.bracket;

import com.meti.unit.CompoundUnit;
import com.meti.unit.Data;
import com.meti.unit.Unit;

public class BracketUnit extends CompoundUnit {
	public BracketUnit(Data data) {
		this(
				new StructUnit(data, data.getDeclareStack()),
				new IfUnit(),
				new ElseUnit(),
				new WhileUnit(),
				new BlockUnit()
		);
	}

	public BracketUnit(Unit... units) {
		super(units);
	}
}
