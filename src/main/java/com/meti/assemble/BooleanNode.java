package com.meti.assemble;

import java.util.Map;

public class BooleanNode extends MapNode {
	BooleanNode(boolean value) {
		this(Map.of(NodeProperty.VALUE, value));
	}

	BooleanNode(Map<NodeProperty, Object> others) {
		super(others);
	}
}
