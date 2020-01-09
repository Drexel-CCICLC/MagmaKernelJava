package com.meti.unit;

import com.meti.declare.Declarations;
import com.meti.declare.TreeDeclarations;
import com.meti.unit.array.ArrayUnit;
import com.meti.unit.bracket.BracketUnit;
import com.meti.unit.external.ExternalUnit;
import com.meti.unit.mutate.MutateUnit;
import com.meti.unit.quantity.value.QuantityUnit;
import com.meti.unit.bracket.StructUnit;

import java.util.Collection;
import java.util.List;

public class MagmaUnit extends ParentUnit {
	public MagmaUnit() {
		this(new TreeDeclarations());
	}

	public MagmaUnit(Declarations declarations) {
		this(List.of(
				new ArrayUnit(),
				new BracketUnit(declarations),
				new ExternalUnit(),
				new MutateUnit(declarations),
				new QuantityUnit(declarations),
				new StructUnit(declarations)
		));
	}

	public MagmaUnit(Collection<Unit> children) {
		super(children);
	}
}
