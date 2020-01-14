package com.meti.declare;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;
import com.meti.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class DeclareParser implements Parser {
    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.contains("=")) {
            int equals = trim.indexOf('=');
            String first = trim.substring(0, equals);
            String last = first.substring(equals + 1);
            int lastSpace = first.lastIndexOf(' ');
            String flagString = first.substring(0, lastSpace);
            List<Flag> flags = Arrays.stream(flagString.split(" "))
                    .map(String::toUpperCase)
                    .map(Flag::valueOf)
                    .collect(Collectors.toList());
            String name = first.substring(lastSpace + 1);
            if(flags.contains(Flag.VAL) || flags.contains(Flag.VAR)) {
                Type type = compiler.resolveValue(last);
                Node valueNode = compiler.compile(last);
                return Optional.of(new DeclareNode(type, name, valueNode));
            }
        }
        return Optional.empty();
    }
}
