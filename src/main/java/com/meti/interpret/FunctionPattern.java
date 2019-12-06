package com.meti.interpret;

import com.meti.assemble.FunctionNode;
import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FunctionPattern implements Pattern {


	private final Map<String, Type> variables;

	public FunctionPattern(Map<String, Type> variables) {
		this.variables = variables;
	}

	@Override
	public Optional<Statement> match(Node node, Interpreter interpreter) {
		if (node instanceof FunctionNode) {
			Map<String, String> parameterStrings = node.getProperty(NodeProperty.PARAMETERS);
			Map<String, Type> parameterMap = new HashMap<>();
			for (Map.Entry<String, String> entry : parameterStrings.entrySet()) {
				parameterMap.put(entry.getKey(), interpreter.resolve(entry.getValue()));
			}
			String returnString = node.getProperty(NodeProperty.RETURN);
			Type returnType = interpreter.resolve(returnString);
			Node content = node.getProperty(NodeProperty.VALUE);
			variables.putAll(parameterMap);
			Statement block = interpreter.interpret(content).orElseThrow();
			parameterMap.keySet().forEach(variables::remove);
			return Optional.of(new FunctionStatement(parameterMap, returnType, block));
		}
		return Optional.empty();
	}
}
