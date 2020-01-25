package com.meti;

import java.util.List;
import java.util.stream.Collectors;

public class BlockNode implements Node {
	private final List<? extends Node> children;

	public BlockNode(List<? extends Node> children) {
		this.children = children;
	}

	@Override
	public String render() {
		return "{" + renderChildren() + "}";
	}

	private String renderChildren() {
		return children.stream()
				.map(Node::render)
				.collect(Collectors.joining());
	}
}
