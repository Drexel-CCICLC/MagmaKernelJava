package com.meti.node.bracket.struct;

import com.meti.node.Node;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StructContentNode implements Node {
	private final List<Node> content;

	public StructContentNode(List<Node> content) {
		this.content = content;
	}

	@Override
	public Deque<Node> children() {
		return new LinkedList<>();
	}

	@Override
	public boolean isParent() {
		return false;
	}

	@Override
	public String render() {
		String renderedContent = content.stream()
				.map(Node::render)
				.collect(Collectors.joining(","));
		return "{" + renderedContent + "}";
	}
}
