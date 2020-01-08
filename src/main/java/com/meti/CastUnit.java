package com.meti;

import java.util.Optional;

class CastUnit implements CompoundUnit {
    @Override
    public boolean canCompile(String value) {
        return value.startsWith("<") && value.contains(">");
    }

    @Override
    public String compile(String value, Compiler compiler) {
        int start = value.indexOf('<');
        int end = value.indexOf('>');
        String typeString = value.substring(start + 1, end);
        Type type = compiler.resolveName(typeString);
        String valueString = value.substring(end + 1);
        return "(" + type.render() + ")" + compiler.compileOnly(valueString);
    }

    @Override
    public Optional<? extends Type> resolveName(String value, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        if (canCompile(value)) {
            int start = value.indexOf('<');
            int end = value.indexOf('>');
            String typeString = value.substring(start + 1, end);
            Type type = compiler.resolveName(typeString);
            return Optional.of(type);
        } else {
            return Optional.empty();
        }
    }
}
