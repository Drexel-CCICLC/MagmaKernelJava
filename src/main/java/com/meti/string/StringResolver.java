package com.meti.string;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.Type;

import java.util.Optional;

public class StringResolver implements Resolver {
    @Override
    public Optional<Type> resolveName(String name, Compiler compiler) {
        String trim = name.trim();
        if("String".equals(trim)) return Optional.of(new StringType());
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.empty();
    }
}
