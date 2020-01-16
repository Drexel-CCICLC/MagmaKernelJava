package com.meti.operator;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Optional;

public class OperationParser implements Parser {
    @Override
    public Optional<Node> parse(String value, Compiler compiler) {
        for (Operation operation : Operations.values()) {
            String operationValue = operation.value();
            if (value.contains(operationValue)) {
                int index = value.indexOf(operationValue);
                String start = value.substring(0, index);
                String stop = value.substring(index + 1);
                Node startNode = compiler.parse(start);
                Node endNode = compiler.parse(stop);
                return Optional.of(new OperationNode(startNode, endNode, operation));
            }
        }
        return Optional.empty();
    }
}
