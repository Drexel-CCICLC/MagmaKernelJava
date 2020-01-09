package com.meti.unit.bracket;

import com.meti.declare.Declarations;
import com.meti.unit.ParentUnit;
import com.meti.unit.Unit;

import java.util.Collection;
import java.util.List;

public class BracketUnit extends ParentUnit {
	public BracketUnit(Declarations declarations) {
		this(List.of(
				new BlockUnit(),
				new InvocationUnit(),
				new StructUnit(declarations)
		));
	}

	public BracketUnit(Collection<Unit> children) {
		super(children);
	}
}
