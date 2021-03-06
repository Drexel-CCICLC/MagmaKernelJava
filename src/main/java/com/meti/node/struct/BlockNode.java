package com.meti.node.struct;

import com.meti.node.Node;

import java.util.Collection;
import java.util.stream.Collectors;

@Deprecated
public class BlockNode implements Node {
	private final Collection<? extends Node> children;

	public BlockNode(Collection<? extends Node> children) {
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
