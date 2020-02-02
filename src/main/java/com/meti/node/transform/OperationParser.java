package com.meti.node.transform;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Arrays;
import java.util.Optional;

public class OperationParser implements Parser {

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Arrays.stream(Operation.values())
				.filter(operation -> containsOperation(operation, content))
				.map(operation -> buildOperation(operation, content, compiler))
				.findFirst();
	}

	private boolean containsOperation(Operation operation, String content) {
		return content.contains(operation.getFrom());
	}

	private Node buildOperation(Operation operation, String content, Compiler compiler) {
		String from = operation.getFrom();
		int fromIndex = content.indexOf(from);
		String before = content.substring(0, fromIndex).trim();
		String after = content.substring(fromIndex + 1).trim();
		Node beforeNode = compiler.parse(before);
		Node afterNode = compiler.parse(after);
		return new OperationNode(beforeNode, afterNode, operation);
	}
}
