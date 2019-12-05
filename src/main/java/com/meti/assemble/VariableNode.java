package com.meti.assemble;

import java.util.Map;

public class VariableNode extends MapNode {
	VariableNode(String value) {
		this(Map.of(
				NodeProperty.VALUE, value
		));
	}

	private VariableNode(Map<NodeProperty, Object> others) {
		super(others);
	}
}
