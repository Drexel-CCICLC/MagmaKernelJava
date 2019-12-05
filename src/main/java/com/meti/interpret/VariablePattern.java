package com.meti.interpret;

import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;
import com.meti.assemble.VariableNode;

import java.util.Optional;

public class VariablePattern implements Pattern {
	@Override
	public Optional<Statement> match(Node node, Interpreter interpreter) {
		if (node instanceof VariableNode) {
			Type type = interpreter.resolve(node);
			String name = node.getProperty(NodeProperty.VALUE);
			return Optional.of(new VariableStatement(type, name));
		} else {
			return Optional.empty();
		}
	}
}
