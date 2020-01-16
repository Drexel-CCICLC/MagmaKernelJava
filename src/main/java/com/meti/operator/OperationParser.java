package com.meti.operator;

import com.meti.Compiler;
import com.meti.Node;
import com.meti.Parser;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class OperationParser implements Parser {
    private Optional<Node> parse(String value, Compiler compiler) {
        for (Operation operation : Operations.values()) {
            String operationValue = operation.value();
            if (value.contains(operationValue)) {
                int index = value.indexOf(operationValue);
                String start = value.substring(0, index);
                String stop = value.substring(index + 1);
                Node startNode = compiler.parseSingle(start);
                Node endNode = compiler.parseSingle(stop);
                return Optional.of(new OperationNode(startNode, endNode, operation));
            }
        }
        return Optional.empty();
    }

	@Override
	public Collection<Node> parseMultiple(String value, Compiler compiler) {
		return parse(value, compiler).stream().collect(Collectors.toSet());
	}
}
