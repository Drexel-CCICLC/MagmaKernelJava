package com.meti.interpret;

import java.util.Set;

public class MagmaInterpreter extends PatternInterpreter {
    public MagmaInterpreter() {
        this(Set.of(new DeclarePattern(), new PrimitivePattern()), Set.of(new PrimitiveResolver()));
    }

    private MagmaInterpreter(Set<Pattern> patterns, Set<Resolver> resolvers) {
        super(patterns, resolvers);
    }
}
