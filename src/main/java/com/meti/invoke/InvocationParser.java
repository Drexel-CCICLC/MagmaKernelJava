package com.meti.invoke;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class InvocationParser implements Parser {
    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.contains("(") && trim.endsWith(")")) {
            int start = trim.indexOf('(');
            int end = trim.indexOf(')');
            String callerString = trim.substring(0, start);
            String argumentString = trim.substring(start + 1, end);
            Node caller = compiler.compile(callerString);
            String[] args = argumentString.split(",");
            List<Node> arguments = Arrays.stream(args)
                    .map(compiler::compile)
                    .collect(Collectors.toList());
            return Optional.of(new InvocationNode(caller, arguments));
        }
        return Optional.empty();
    }
}
