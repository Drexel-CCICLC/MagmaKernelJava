package com.meti;

import java.util.Optional;
import java.util.Set;

public class FactoryParser implements Parser {
	private final Set<NodeFactory> nodeFactories;

	public FactoryParser(NodeFactory... nodeFactories) {
		this(Set.of(nodeFactories));
	}

	public FactoryParser(Set<NodeFactory> nodeFactories) {
		this.nodeFactories = nodeFactories;
	}

	private IllegalStateException fail(String s) {
		return new IllegalStateException(s);
	}

	@Override
	public Node parse(String value) {
		return nodeFactories.stream()
				.map(nodeFactory -> nodeFactory.parse(value, this))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> fail(value));
	}
}
