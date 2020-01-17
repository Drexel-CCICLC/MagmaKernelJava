package com.meti.node.value.compound.variable;

import com.meti.compile.Compiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.node.Resolver;
import com.meti.node.Type;

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
