package com.meti.assemble;

import java.util.Map;

import static com.meti.assemble.NodeProperty.*;

public class FunctionNode extends MapNode {
	FunctionNode(Map<Object, Object> parameters, String returnType, Node content) {
		this(Map.of(
				PARAMETERS, parameters,
				RETURN, returnType,
				VALUE, content
		));
	}

	FunctionNode(Map<NodeProperty, Object> others) {
		super(others);
	}
}
