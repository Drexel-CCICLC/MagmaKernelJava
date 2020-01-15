package com.meti.struct;

import com.meti.Compiler;
import com.meti.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StructParser implements Parser {
    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
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
            int returnTypeEnd = (trim.indexOf(':') == -1) ? trim.length() : trim.indexOf(':');
            String returnTypeString = trim.substring(returnTypeStart, returnTypeEnd);
            Type returnType = compiler.resolveName(returnTypeString);
            return Optional.of(new EmptyNode());
        }
        return Optional.empty();
    }
}
