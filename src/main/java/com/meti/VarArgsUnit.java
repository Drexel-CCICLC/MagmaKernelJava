package com.meti;

import java.util.Optional;

class VarArgsUnit implements Unit {
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
        if (value.endsWith("...")) {
            int index = value.indexOf('.');
            Type child = compiler.resolveName(value.substring(0, index));
            return Optional.of(new Type(".", child));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.empty();
    }
}
