package com.meti.unit.quantity.transform;

import com.meti.compile.ComplexCompiler;
import com.meti.declare.Declaration;
import com.meti.declare.Declarations;
import com.meti.exception.DoesNotExistException;
import com.meti.type.Type;
import com.meti.unit.CompoundUnit;

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
    public String compile(String value, ComplexCompiler compiler) {
        return declarations.relative(value)
                .map(Declaration::render)
                .orElseThrow(() -> new DoesNotExistException(value + " is not defined."));
    }

    @Override
    public Optional<? extends Type> resolveName(String value, ComplexCompiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, ComplexCompiler compiler) {
        return Optional.of(value)
                .map(String::trim)
                .flatMap(declarations::relative)
                .map(Declaration::typeOf);
    }
}
