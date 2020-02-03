package com.meti.node.struct;

import com.meti.exception.RenderException;
import com.meti.node.Node;
import com.meti.node.Type;
import com.meti.node.primitive.VoidType;

import java.util.List;
import java.util.stream.Collectors;

public class InvocationNode implements Node {
	private final List<? extends Node> arguments;
	private final Node caller;
	private final Type type;

	public InvocationNode(Node caller, List<? extends Node> arguments, Type type) {
		this.caller = caller;
		this.arguments = arguments;
		this.type = type;
	}

	@Override
	public String render() {
		String joinedArgs = arguments.stream()
				.map(Node::render)
				.collect(Collectors.joining(","));
		String end;
		if (type instanceof FunctionType) {
			Type returnType = ((FunctionType) this.type).returnType();
			end = returnType.equals(VoidType.INSTANCE) ? ";" : "";
		} else {
			throw new RenderException(type + " is not a function.");
		}
		return caller.render() + "(" + joinedArgs + ")" + end;
	}
}
