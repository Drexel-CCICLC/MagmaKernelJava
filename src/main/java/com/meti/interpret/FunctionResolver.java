package com.meti.interpret;

import com.meti.assemble.FunctionNode;
import com.meti.assemble.Node;
import com.meti.assemble.NodeProperty;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FunctionResolver implements Resolver {
	@Override
	public Optional<Type> resolve(Node node, Interpreter interpreter) {
		if (node instanceof FunctionNode) {
			Map<String, Type> parameters = node.getProperty(NodeProperty.PARAMETERS);
			List<Type> parameterTypes = parameters.keySet()
					.stream()
					.map(parameters::get)
					.collect(Collectors.toList());
			String returnString = node.getProperty(NodeProperty.RETURN);
			Type returnType = interpreter.resolve(returnString);
			return Optional.of(new FunctionType(parameterTypes, returnType));
		}
		return Optional.empty();
	}

	@Override
	public Optional<? extends Type> resolve(String value) {
		//TODO: implement
		return Optional.empty();
	}
}
