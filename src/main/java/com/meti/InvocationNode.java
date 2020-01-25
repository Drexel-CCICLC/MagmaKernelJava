package com.meti;

import java.util.List;
import java.util.stream.Collectors;

public class InvocationNode implements Node {
	private final List<? extends Node> arguments;
	private final Node caller;

	public InvocationNode(Node caller, List<? extends Node> arguments) {
		this.caller = caller;
		this.arguments = arguments;
	}

	@Override
	public String render() {
		String joinedArgs = arguments.stream()
				.map(Node::render)
				.collect(Collectors.joining(","));
		return caller.render() + "(" + joinedArgs + ")";
	}
}
