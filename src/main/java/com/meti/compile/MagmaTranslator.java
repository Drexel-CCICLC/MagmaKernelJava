package com.meti.compile;

import java.util.Set;

public class MagmaTranslator extends UnitTranslator {
    public MagmaTranslator() {
        this(Set.of(new DeclareUnit(), new IntUnit()));
    }

    MagmaTranslator(Set<Unit> units) {
        super(units);
    }
}
