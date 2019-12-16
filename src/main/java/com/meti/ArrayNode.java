package com.meti;

import java.util.List;
import java.util.stream.Collectors;

final class ArrayNode extends AbstractParentNode {
	public ArrayNode(Struct struct, List<Node> children) {
		super(struct, children);
	}

	@Override
	public String compile(Aliaser aliaser, NodeTree tree) {
		String content = children.stream()
				.map(node -> node.compile(aliaser, tree))
				.collect(Collectors.joining(","));
		return "[" + content + "]";
	}

	@Override
	public Node transform() {
		return this;
	}
}
