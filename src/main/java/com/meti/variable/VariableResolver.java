package com.meti.variable;

import com.meti.*;
import com.meti.Compiler;

import java.util.Optional;

public class VariableResolver implements Resolver {
    private final Declarations declarations;

    public VariableResolver(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public Optional<Type> resolveName(String name, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return declarations.relative(value.trim())
                .map(Declaration::type);
    }
}
