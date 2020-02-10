package com.meti.node.transform.operate;

import com.meti.Compiler;
import com.meti.Parser;
import com.meti.node.Node;

import java.util.Arrays;
import java.util.Optional;

public class OperationParser implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Arrays.stream(Operations.values())
				.filter(operation -> operation.isPresent(content))
				.map(operation -> operation.toNode(content, compiler::parse))
				.findFirst();
	}
}
