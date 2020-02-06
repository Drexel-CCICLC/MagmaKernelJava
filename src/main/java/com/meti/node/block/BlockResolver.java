package com.meti.node.block;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;
import com.meti.node.struct.type.FunctionType;
import com.meti.parse.Declaration;
import com.meti.parse.Declarations;

import java.util.Optional;

public class BlockResolver implements Resolver {
    private final Declarations declarations;

    public BlockResolver(Declarations declarations) {
        this.declarations = declarations;
    }

    @Override
    public Optional<Type> resolveName(String content, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String content, Compiler compiler) {
        String trim = content.trim();
        if (trim.equals("{}")) {
            String singletonName = declarations.currentName();
            Optional<Declaration> singletonDeclaration = declarations.relative(singletonName + "$");
            if (singletonDeclaration.isPresent()) {
                Type type = singletonDeclaration.get().type();
                if (type instanceof FunctionType) {
                    return Optional.of(((FunctionType) type).returnType());
                } else {
                    throw new IllegalStateException("Not a function: " + singletonName);
                }
            } else {
                throw new IllegalStateException("Failed to find singleton: " + singletonName);
            }
        }
        return Optional.empty();
    }
}
