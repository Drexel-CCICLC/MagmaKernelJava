package com.meti.interpret;

import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;
import com.meti.assemble.WhileNode;

import java.util.Optional;

public class WhilePattern implements Pattern {
	@Override
	public Optional<Statement> match(Node node, Interpreter interpreter) {
		if (node instanceof WhileNode) {
			Node conditionNode = node.getProperty(NodeProperty.CONDITION);
			Node ifNode = node.getProperty(NodeProperty.VALUE);
			Type type = interpreter.resolve(conditionNode);
			if (!type.equals(PrimitiveType.BOOLEAN)) {
				throw new IllegalArgumentException("Type of condition " + type + " is not a boolean.");
			}
			Statement condition = interpreter.interpret(conditionNode).orElseThrow();
			Statement content = interpreter.interpret(ifNode).orElseThrow();
			return Optional.of(new WhileStatement(condition, content));
		}
		return Optional.empty();
	}
}
