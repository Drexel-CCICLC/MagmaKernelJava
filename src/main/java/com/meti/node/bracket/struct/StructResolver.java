package com.meti.node.bracket.struct;

import com.meti.compile.Compiler;
import com.meti.node.Resolver;
import com.meti.node.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StructResolver implements Resolver {
    @Override
    public Optional<Type> resolveName(String name, Compiler compiler) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> resolveValue(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.startsWith("(")) {
            int paramStart = trim.indexOf('(') + 1;
            int paramEnd = trim.indexOf(')');
            String paramsString = trim.substring(paramStart, paramEnd);
            List<Type> parameters = Arrays.stream(paramsString.split(","))
                    .map(String::trim)
                    .filter(paramString -> !paramString.isBlank())
                    .map(paramString -> paramString.split(" "))
                    .map(paramArgs -> paramArgs[0])
                    .map(compiler::resolveName)
                    .collect(Collectors.toList());
            int returnTypeStart = trim.indexOf("=>") + 2;
            int returnTypeEnd = (-1 == trim.indexOf(':')) ? trim.length() : trim.indexOf(':');
            String returnTypeString = trim.substring(returnTypeStart, returnTypeEnd);
            Type returnType = compiler.resolveName(returnTypeString);
            return Optional.of(StructTypeBuilder.create().withReturnType(returnType).withName(null).withParameters(parameters).build());
        }
        return Optional.empty();
    }
}
