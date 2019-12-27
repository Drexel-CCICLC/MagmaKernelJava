package com.meti.unit.value;

import com.meti.unit.CompoundUnit;
import com.meti.unit.Data;
import com.meti.unit.Unit;

public class ValueUnit extends CompoundUnit {
	public ValueUnit(Data data) {
		this(new OperationUnit(),
				new StringUnit(data),
				new InvocationUnit(data),
				new PrimitiveUnit(data),
				new VariableUnit(data));
	}

	public ValueUnit(Unit... units) {
		super(units);
	}
}
