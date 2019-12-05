package com.meti.interpret;

import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;
import com.meti.assemble.VariableNode;

import java.util.Map;
import java.util.Optional;

public class VariableResolver implements Resolver {
	private final Map<String, Type> variables;

	public VariableResolver(Map<String, Type> variables) {
		this.variables = variables;
	}

	@Override
	public Optional<Type> resolve(Node node) {
		if (node instanceof VariableNode) {
			String variableName = node.getProperty(NodeProperty.VALUE);
			if(!variables.containsKey(variableName)) {
				throw new IllegalArgumentException(variableName + " is not defined.");
			}
			return Optional.of(variables.get(variableName));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<? extends Type> resolve(String value) {
		return Optional.empty();
	}
}
