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
        String trim = value.trim();
        return Optional.of(trim.contains(".") ? parseAccesor(compiler, trim) : parseSimple(trim));
    }

    private Type parseAccesor(Compiler compiler, String trim) {
        int period = trim.indexOf('.');
        String parentString = trim.substring(0, period);
        String childString = trim.substring(period + 1);
        Type type = compiler.resolveValue(parentString);
        return type.childType(childString).orElseThrow();
    }

    private Type parseSimple(String childName) {
        Optional<Declaration> parentOptional = declarations.parentOf(childName);
        if (parentOptional.isPresent()) {
            Declaration parent = parentOptional.get();
            if (!declarations.isRoot(parent) && !declarations.isCurrent(parent) && parent.hasChildAsParameter(childName)) {
                return parent.child(childName)
                        .map(Declaration::type)
                        .orElseThrow();
            }
        }
        return buildInScope(childName);
    }

    private Type buildInScope(String childName) {
        return declarations.relative(childName)
                .map(Declaration::type)
                .orElseThrow();
    }
}
