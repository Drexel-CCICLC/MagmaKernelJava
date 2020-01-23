package com.meti.node.value.primitive.array;

import com.meti.node.Node;
import com.meti.node.bracket.struct.GeneratedNodeBuilder;
import com.meti.node.bracket.struct.Generator;
import com.meti.node.bracket.struct.IncrementedGenerator;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListedCache implements Cache {
	private final Generator generator = new IncrementedGenerator();
	private final Deque<Node> nodes = new LinkedList<>();

	@Override
	public void addFirst(GeneratedNodeBuilder builder) {
		addFirst(builder.create(generator));
	}

	@Override
	public void addFirst(Node node) {
		nodes.addFirst(node);
	}

	@Override
	public void addLast(Node node) {
        nodes.addLast(node);
	}

	@Override
	public String render() {
		return nodes.stream()
				.filter(Objects::nonNull)
				.map(Node::render)
				.collect(Collectors.joining());
	}
}
