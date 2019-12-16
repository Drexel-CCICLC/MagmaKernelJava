package com.meti;

import java.util.List;
import java.util.stream.Collectors;

final class BlockNode extends AbstractParentNode {
	public BlockNode(List<Node> children) {
		this(null, children);
	}

	public BlockNode(Struct struct, List<Node> children) {
		super(struct, children);
	}

	@Override
	public String compile(Aliaser aliaser, NodeTree tree) {
		var values = children.stream()
				.map(node -> node.compile(aliaser, tree))
				.collect(Collectors.joining());
		return "{" + values + "}";
	}

	@Override
	public Node transform() {
		return this;
	}
}
