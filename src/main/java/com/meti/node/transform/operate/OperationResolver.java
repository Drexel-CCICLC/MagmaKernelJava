package com.meti.node.transform.operate;

import com.meti.Compiler;
import com.meti.Resolver;
import com.meti.node.Type;

import java.util.Arrays;
import java.util.Optional;

public class OperationResolver implements Resolver {
    @Override
    public Optional<Type> resolveName(String content, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String content, Compiler compiler) {
        return Arrays.stream(Operations.values())
                .filter(operation -> operation.isPresent(content))
                .map(operation -> operation.toType(content, compiler::resolveValue))
                .findFirst();
    }
}
