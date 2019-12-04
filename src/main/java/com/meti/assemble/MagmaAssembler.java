package com.meti.assemble;

import java.util.List;
import java.util.Set;

public class MagmaAssembler extends MoldAssembler {
    public MagmaAssembler() {
        this(List.of(
                DeclareMold::new,
                IntegerMold::new
        ));
    }

    MagmaAssembler(List<? extends NodeMoldFactory> moldFactories) {
        super(moldFactories);
    }
}
