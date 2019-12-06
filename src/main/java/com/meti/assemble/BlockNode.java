package com.meti.assemble;

import java.util.List;
import java.util.Map;

public class BlockNode extends MapNode {
	BlockNode(List<Node> content) {
		this(Map.of(
				NodeProperty.CONTENT, content
		));
	}

	BlockNode(Map<NodeProperty, Object> others) {
		super(others);
	}
}
