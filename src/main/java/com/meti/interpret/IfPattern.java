package com.meti.interpret;

import com.meti.assemble.IfNode;
import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;

import java.util.Optional;

public class IfPattern implements Pattern {
	@Override
	public Optional<Statement> match(Node node, Interpreter interpreter) {
		if (node instanceof IfNode) {
			Node conditionNode = node.getProperty(NodeProperty.CONDITION);
			Node ifNode = node.getProperty(NodeProperty.VALUE);
			Node elseNode = node.getProperty(NodeProperty.ALTERNATE);
			Type type = interpreter.resolve(conditionNode);
			if (!type.equals(PrimitiveType.BOOLEAN)) {
				throw new IllegalArgumentException("Type of condition " + type + " is not a boolean.");
			}
			Statement condition = interpreter.interpret(conditionNode).orElseThrow();
			Statement ifStatement = interpreter.interpret(ifNode).orElseThrow();
			Statement elseStatement = interpreter.interpret(elseNode).orElseThrow();
			return Optional.of(new IfStatement(condition, ifStatement, elseStatement));
		}
		return Optional.empty();
	}
}
