package com.meti;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class InvocationNode extends AbstractParentNode {
	private final Node caller;

	public InvocationNode(Struct struct, Node caller, List<Node> children) {
		super(struct, children);
		this.caller = caller;
	}

	@Override
	public String compile(Aliaser aliaser, NodeTree tree) {
		var argString = IntStream.range(1, children.size())
				.mapToObj(children::get)
				.map(node -> node.compile(aliaser, tree))
				.collect(Collectors.joining(","));
		var ending = (struct == null) ? ";" : "";
		return caller.compile(aliaser, tree) + "(" + argString + ")" + ending;
	}

	@Override
	public Node transform() {
		return this;
	}
}
