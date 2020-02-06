package com.meti.node.struct;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;
import com.meti.node.declare.Declaration;
import com.meti.node.declare.Declarations;

import java.util.Optional;

public class ObjectResolver implements Resolver {
    private final Declarations declarations;

    public ObjectResolver(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public Optional<Type> resolveName(String content, Compiler compiler) {
        String trim = content.trim();
        Optional<Declaration> child = declarations.relative(trim);
        Type type = child.isEmpty() ?
                declarations.toCurrentClass(trim) :
                new DefinedObjectType(child.get());
        return Optional.of(type);
    }

    @Override
    public Optional<Type> resolveValue(String content, Compiler compiler) {
        return Optional.empty();
    }
}
