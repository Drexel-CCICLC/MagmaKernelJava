package com.meti;

import java.util.List;
import java.util.Optional;

public class FactoryParser implements Parser {
	private final List<NodeFactory> nodeFactories;

	public FactoryParser(NodeFactory... nodeFactories) {
		this(List.of(nodeFactories));
	}

	public FactoryParser(List<NodeFactory> nodeFactories) {
		this.nodeFactories = nodeFactories;
	}

	@Override
	public Node parse(String value) {
		return parse(value, null);
	}

	@Override
	public Node parse(String value, Node parent) {
		return nodeFactories.stream()
				.map(nodeFactory -> nodeFactory.parse(value, this, parent))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> fail(value));
	}

	@Override
	public Struct resolve(String value) {
		return nodeFactories.stream()
				.map(nodeFactory -> nodeFactory.parse(value))
				.flatMap(Optional::stream)
				.findAny()
				.orElseThrow(() -> fail(value));
	}

	private IllegalStateException fail(String s) {
		return new IllegalStateException(s);
	}
}
