package com.meti.unit.array;

import com.meti.unit.ParentUnit;
import com.meti.unit.Unit;

import java.util.Collection;
import java.util.List;

public class ArrayUnit extends ParentUnit {
	public ArrayUnit() {
		this(List.of(new ArrayIndexUnit(), new ArrayNewUnit()));
	}

	public ArrayUnit(Collection<Unit> children) {
		super(children);
	}
}
