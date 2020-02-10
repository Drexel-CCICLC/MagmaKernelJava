package com.meti.node.block;

import com.meti.node.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class BlockNode implements Node {
	private final Collection<? extends Node> children;

	public BlockNode(Node node) {
		this(Collections.singleton(node));
	}

	public BlockNode(Collection<? extends Node> children) {
		this.children = children;
	}

	@Override
	public String render() {
		return "{" + joinChildren() + "}";
	}

	private String joinChildren() {
		return children.stream()
				.map(Node::render)
				.collect(Collectors.joining());
	}
}
