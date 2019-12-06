package com.meti.assemble;

import java.util.Map;

public class IfNode extends MapNode {
	IfNode(Node condition, Node ifBlock, Node elseBlock) {
		this(Map.of(
				NodeProperty.CONDITION, condition,
				NodeProperty.VALUE, ifBlock,
				NodeProperty.ALTERNATE, elseBlock
		));
	}

	IfNode(Map<NodeProperty, Object> others) {
		super(others);
	}
}
