package com.meti.assemble;

import java.util.List;

public class MagmaAssembler extends MoldAssembler {
	public MagmaAssembler() {
		this(List.of(
				WhileMold::new,
				IfMold::new,
				BlockMold::new,
				DeclareMold::new,
				AssignMold::new,
				IntMold::new,
				BooleanMold::new,
				StringMold::new,
				VariableMold::new
		));
	}

	private MagmaAssembler(List<? extends NodeMoldFactory> moldFactories) {
		super(moldFactories);
	}
}
