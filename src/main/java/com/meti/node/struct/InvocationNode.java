package com.meti.node.struct;

import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.primitive.VoidType;

import java.util.List;
import java.util.stream.Collectors;

public class InvocationNode implements Node {
	private final List<? extends Node> arguments;
	private final Node caller;
	private final Type returnType;

	public InvocationNode(Node caller, List<? extends Node> arguments, Type returnType) {
		this.caller = caller;
		this.arguments = arguments;
		this.returnType = returnType;
	}

	@Override
	public String render() {
		String joinedArgs = arguments.stream()
				.map(Node::render)
				.collect(Collectors.joining(","));
		String end = returnType.equals(VoidType.INSTANCE) ? ";" : "";
		return caller.render() + "(" + joinedArgs + ")" + end;
	}
}
