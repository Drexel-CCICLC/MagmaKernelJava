package com.meti.unit;

import com.meti.compile.Compiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.exception.DoesNotExistException;
import com.meti.type.Type;

import java.util.Optional;

public class VariableUnit implements CompoundUnit {
    private final Declarations declarations;

    public VariableUnit(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public boolean canCompile(String value) {
        return true;
    }

    @Override
    public String compile(String value, Compiler compiler) {
        return declarations.relative(value)
                .map(Declaration::render)
                .orElseThrow(() -> new DoesNotExistException(value + " is not defined."));
    }

    @Override
    public Optional<? extends Type> resolveName(String value, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        return Optional.of(value)
                .map(String::trim)
                .flatMap(declarations::relative)
                .map(Declaration::typeOf);
    }
}
