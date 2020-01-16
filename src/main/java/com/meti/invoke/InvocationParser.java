package com.meti.invoke;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;
import com.meti.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvocationParser implements Parser {
    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        String trim = value.trim();
        if (trim.contains("(") && trim.endsWith(")")) {
            int start = trim.indexOf('(');
            int end = trim.indexOf(')');
            String callerString = trim.substring(0, start);
            String argumentString = trim.substring(start + 1, end);
            Type callerType = compiler.resolveValue(callerString);
            Node caller = compiler.parse(callerString);
            String[] args = argumentString.split(",");
            List<Node> arguments = Arrays.stream(args)
                    .filter(arg -> !arg.isBlank())
                    .map(compiler::parse)
                    .collect(Collectors.toList());
            return Optional.of(new InvocationNode(callerType, caller, arguments));
        }
        return Optional.empty();
    }
}
