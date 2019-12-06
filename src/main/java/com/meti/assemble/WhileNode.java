package com.meti.assemble;

import java.util.Map;

public class WhileNode extends MapNode {
	WhileNode(Node condition, Node content) {
		this(Map.of(
				NodeProperty.CONDITION, condition,
				NodeProperty.VALUE, content
		));
	}

	WhileNode(Map<NodeProperty, Object> others) {
		super(others);
	}
}
