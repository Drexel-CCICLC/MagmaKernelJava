package com.meti.node.struct.invoke;

import com.meti.node.Node;

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
		String joinedArgs = joinArgs();
		return caller.render() + "(" + joinedArgs + ")";
	}

	private String joinArgs() {
		return arguments.stream()
				.map(Node::render)
				.collect(Collectors.joining(","));
	}
}
