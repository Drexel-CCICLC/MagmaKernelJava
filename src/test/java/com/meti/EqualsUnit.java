package com.meti;

import java.util.Map;

public class EqualsUnit extends CompoundUnit {
	public EqualsUnit(Declarations declarations) {
		this(new DeclareUnit(declarations));
	}

	public EqualsUnit(Unit... children) {
		super(children);
	}
}
