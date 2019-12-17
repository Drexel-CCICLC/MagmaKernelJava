package com.meti.unit;

public class ValueUnit extends CompoundUnit {
	public ValueUnit(Data data) {
		this(new OperationUnit(),
				new PrimitiveUnit(),
				new VariableUnit(data));
	}

	public ValueUnit(Unit... units) {
		super(units);
	}
}
