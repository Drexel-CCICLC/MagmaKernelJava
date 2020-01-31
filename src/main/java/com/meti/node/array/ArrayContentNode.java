package com.meti.node.array;

import com.meti.node.Node;

import java.util.Collection;
import java.util.stream.Collectors;

public class ArrayContentNode implements Node {
	private final Collection<? extends Node> children;

	public ArrayContentNode(Collection<? extends Node> children) {
		this.children = children;
	}

	@Override
	public String render() {
		String collect = children.stream()
				.map(Node::render)
				.collect(Collectors.joining(","));
		return "{" + collect + "}";
	}
}
