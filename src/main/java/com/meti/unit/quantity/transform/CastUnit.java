package com.meti.unit.quantity.transform;

import com.meti.compile.ComplexCompiler;
import com.meti.type.Type;
import com.meti.unit.CompoundUnit;

import java.util.Optional;

public class CastUnit implements CompoundUnit {
    @Override
    public boolean canCompile(String value) {
        return value.startsWith("<") && value.contains(">");
    }

    @Override
    public String compile(String value, ComplexCompiler compiler) {
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
    public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
        return Optional.of(value)
                .filter(this::canCompile)
                .map(string -> resolveType(string, compiler));
    }

    private Type resolveType(String value, ComplexCompiler compiler) {
        int start = startOfCast(value);
        int end = endOfCast(value);
        String typeString = value.substring(start + 1, end);
        return compiler.resolveName(typeString);
    }
}
