package com.meti.other;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.Type;

import java.util.Optional;

public class AnyResolver implements Resolver {
    @Override
    public Optional<Type> resolveName(String name, Compiler compiler) {
        String trim = name.trim();
        if("Any".equals(trim)) return Optional.of(new AnyType());
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.empty();
    }
}
