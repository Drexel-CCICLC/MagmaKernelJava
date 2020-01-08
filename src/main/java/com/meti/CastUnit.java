package com.meti;

import java.util.Optional;

class CastUnit implements CompoundUnit {
    @Override
    public boolean canCompile(String value) {
        return value.startsWith("<") && value.contains(">");
    }

    @Override
    public String compile(String value, Compiler compiler) {
        int end = endOfCast(value);
        Type type = resolveType(value, compiler);
        String valueString = value.substring(end + 1);
        return "(" + type.render() + ")" +
                compiler.compileOnly(valueString);
    }

    private int endOfCast(String value) {
        return value.indexOf('>');
    }

    private int startOfCast(String value) {
        return value.indexOf('<');
    }

    @Override
    public Optional<? extends Type> resolveName(String value, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.of(value)
                .filter(this::canCompile)
                .map(string -> resolveType(string, compiler));
    }

    private Type resolveType(String value, Compiler compiler) {
        int start = startOfCast(value);
        int end = endOfCast(value);
        String typeString = value.substring(start + 1, end);
        return compiler.resolveName(typeString);
    }
}
