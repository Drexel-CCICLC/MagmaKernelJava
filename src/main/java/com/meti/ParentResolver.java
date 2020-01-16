package com.meti;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ParentResolver implements Resolver {
    private final Collection<Resolver> children;

    public ParentResolver(Resolver... children) {
        this(Arrays.asList(children));
    }

    ParentResolver(Collection<Resolver> children) {
        this.children = children;
    }

    @Override
    public Optional<Type> resolveName(String name, Compiler compiler) {
        return children.stream()
                .map(child -> child.resolveName(name, compiler))
                .flatMap(Optional::stream)
                .findFirst();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return children.stream()
                .map(child -> child.resolveValue(value, compiler))
                .flatMap(Optional::stream)
                .findFirst();
    }
}
