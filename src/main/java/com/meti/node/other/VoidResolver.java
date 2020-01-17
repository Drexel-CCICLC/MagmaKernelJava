package com.meti.node.other;

import com.meti.compile.Compiler;
import com.meti.node.Resolver;
import com.meti.node.Type;

import java.util.Optional;

public class VoidResolver implements Resolver {
    @Override
    public Optional<Type> resolveName(String name, Compiler compiler) {
        String trim = name.trim();
        if ("Void".equals(trim)) return Optional.of(VoidType.INSTANCE());
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.empty();
    }
}
