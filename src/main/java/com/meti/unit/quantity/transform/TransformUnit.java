package com.meti.unit.quantity.transform;

import com.meti.declare.Declarations;
import com.meti.unit.ParentUnit;
import com.meti.unit.Unit;

import java.util.Collection;
import java.util.List;

public class TransformUnit extends ParentUnit {
	public TransformUnit(Declarations declarations) {
		this(List.of(
				new CastUnit(),
				new OperationUnit(),
				new ReturnUnit(),
				new VariableUnit(declarations)
		));
	}

	public TransformUnit(Collection<Unit> children) {
		super(children);
	}
}
