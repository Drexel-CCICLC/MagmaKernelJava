package com.meti;

import java.util.Optional;

class AnyUnit implements Unit {
    @Override
    public boolean canCompile(String value) {
        return false;
    }

    @Override
    public String compile(String value, Compiler compiler) {
        return null;
    }

    @Override
    public Optional<Type> resolveName(String value, Compiler compiler) {
        return Optional.of(new Type("void*")).filter(s -> value.equals("Any"));
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.empty();
    }
}
