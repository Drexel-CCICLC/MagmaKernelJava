package com.meti.unit.quantity.value;

import com.meti.unit.ParentUnit;
import com.meti.unit.Unit;

import java.util.Collection;
import java.util.List;

public class ValueUnit extends ParentUnit {
	public ValueUnit() {
		this(List.of(
				new CharUnit(),
				new IntUnit(),
				new StringUnit()
		));
	}

	public ValueUnit(Collection<Unit> children) {
		super(children);
	}
}
