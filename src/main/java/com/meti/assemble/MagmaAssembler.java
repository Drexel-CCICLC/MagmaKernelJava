package com.meti.assemble;

import java.util.List;

public class MagmaAssembler extends MoldAssembler {
	public MagmaAssembler() {
		this(List.of(
				DeclareMold::new,
				AssignMold::new,
				IntegerMold::new,
				StringMold::new,
                VariableMold::new
		));
	}

	private MagmaAssembler(List<? extends NodeMoldFactory> moldFactories) {
		super(moldFactories);
	}
}
