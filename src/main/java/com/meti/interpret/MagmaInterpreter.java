package com.meti.interpret;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MagmaInterpreter extends PatternInterpreter {
    public MagmaInterpreter() {
        this(new HashMap<>());
    }

    public MagmaInterpreter(Map<String, Type> variables) {
        this(Set.of(
                new AssignPattern(variables),
                new DeclarePattern(variables),
                new PrimitivePattern()),
                Set.of(new PrimitiveResolver()));
    }

    private MagmaInterpreter(Set<Pattern> patterns, Set<Resolver> resolvers) {
        super(patterns, resolvers);
    }
}
