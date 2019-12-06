package com.meti.interpret;

import com.meti.assemble.BlockNode;
import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockPattern implements Pattern {
	@Override
	public Optional<Statement> match(Node node, Interpreter interpreter) {
		if (node instanceof BlockNode) {
			List<Node> contentNodes = node.getProperty(NodeProperty.CONTENT);
			List<Statement> statements = contentNodes.stream()
					.map(interpreter::interpret)
					.flatMap(Optional::stream)
					.collect(Collectors.toList());
			return Optional.of(new BlockStatement(statements));
		}
		return Optional.empty();
	}
}
