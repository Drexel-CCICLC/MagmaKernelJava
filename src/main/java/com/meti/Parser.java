package com.meti;

import java.util.Optional;
import java.util.Set;

public class Parser {
	private final Set<NodeFactory> nodeFactories;

	public Parser(NodeFactory... nodeFactories) {
		this(Set.of(nodeFactories));
	}

	public Parser(Set<NodeFactory> nodeFactories) {
		this.nodeFactories = nodeFactories;
	}

	private IllegalStateException fail(String s) {
		return new IllegalStateException(s);
	}

	public Node parse(String value) {
		return nodeFactories.stream()
				.map(nodeFactory -> nodeFactory.parse(value, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> fail(value));
	}
}
