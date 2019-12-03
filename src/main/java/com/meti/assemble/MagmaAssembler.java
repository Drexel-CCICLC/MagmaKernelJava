package com.meti.assemble;

import java.util.Set;

public class MagmaAssembler extends MoldAssembler {
    public MagmaAssembler() {
        this(Set.of(
                IntegerMold::new,
                DeclareMold::new
        ));
    }

    MagmaAssembler(Set<? extends NodeMoldFactory> moldFactories) {
        super(moldFactories);
    }
}
