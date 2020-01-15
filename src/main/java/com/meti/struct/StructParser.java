package com.meti.struct;

import com.meti.Compiler;
import com.meti.*;
import com.meti.block.BlockNode;

import java.util.*;
import java.util.stream.Collectors;

public class StructParser implements Parser {
    private final Declarations declarations;
    private final List<Node> functions;

    public StructParser(Declarations declarations, List<Node> functions) {
        this.declarations = declarations;
        this.functions = functions;
    }

    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.startsWith("(")) {
            int paramStart = trim.indexOf('(') + 1;
            int paramEnd = trim.indexOf(')');
            String paramsString = trim.substring(paramStart, paramEnd);
            Map<String, Type> parameters = Arrays.stream(paramsString.split(","))
                    .map(String::trim)
                    .filter(paramString -> !paramString.isBlank())
                    .map(paramString -> paramString.split(" "))
                    .collect(Collectors.toMap(
                            strings -> strings[1],
                            strings -> compiler.resolveName(strings[0])));
            int returnTypeStart = trim.indexOf("=>") + 2;
            int returnTypeEnd = trim.contains(":") ? trim.indexOf(':') : trim.length();
            String returnTypeString = trim.substring(returnTypeStart, returnTypeEnd);
            Type returnType = compiler.resolveName(returnTypeString);
            Node block;
            if (trim.contains(":")) {
                String blockString = trim.substring(trim.indexOf(':') + 1);
                Node impl = compiler.parse(blockString);
                if (impl.isParent()) {
                    block = impl;
                } else {
                    block = new BlockNode(Collections.singleton(impl));
                }
            } else {
                throw new UnsupportedOperationException("Abstract methods are not supported yet.");
            }
            functions.add(new StructNode(returnType, declarations.current().name(), parameters, block));
            return Optional.of(new EmptyNode());
        }
        return Optional.empty();
    }
}
