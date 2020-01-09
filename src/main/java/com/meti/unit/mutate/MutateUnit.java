package com.meti.unit.mutate;

import com.meti.declare.Declarations;
import com.meti.unit.ParentUnit;
import com.meti.unit.Unit;

import java.util.Collection;
import java.util.List;

public class MutateUnit extends ParentUnit {
	public MutateUnit(Declarations declarations) {
		this(List.of(new DeclareUnit(declarations), new AssignUnit()));
	}

	public MutateUnit(Collection<Unit> children) {
		super(children);
	}
}
