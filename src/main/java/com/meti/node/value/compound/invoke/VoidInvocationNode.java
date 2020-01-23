package com.meti.node.value.compound.invoke;

import com.meti.node.Node;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class VoidInvocationNode implements Node {
	private final Collection<? extends Node> args;
	private final Node caller;

    VoidInvocationNode(Node caller, Collection<? extends Node> args) {
		this.caller = caller;
		this.args = args;
	}

	@Override
	public LinkedList<Node> children() {
		return new LinkedList<>();
	}

	@Override
	public boolean isParent() {
		return false;
	}

	@Override
	public String render() {
		String argsString = args.stream()
				.map(Node::render)
				.collect(Collectors.joining(","));
		return caller.render() + "(" + argsString + ");";
	}
}
