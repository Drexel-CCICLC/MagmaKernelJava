package com.meti.unit.value;

import com.meti.unit.CompoundUnit;
import com.meti.unit.Data;
import com.meti.unit.Unit;

public class ValueUnit extends CompoundUnit {
	public ValueUnit(Data data) {
		this(new StringUnit(),
				new InvocationUnit(),
				new OperationUnit(),
				new PrimitiveUnit(),
				new VariableUnit(data));
	}

	public ValueUnit(Unit... units) {
		super(units);
	}
}
