package com.meti.node.bracket.struct;

import com.meti.declare.Parameter;
import com.meti.node.Node;
import com.meti.node.Type;

import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class FunctionNode implements Node {
	private final Node block;
	private final String name;
	private final Set<? extends Parameter> parameters;
	private final Type returnType;

	FunctionNode(String name, Set<? extends Parameter> parameters, Type returnType, Node block) {
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
		this.block = block;
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
		String paramString = joinParams();
		return returnType.render() + " " + name + "(" + paramString + ")" + block.render();
	}

	private String joinParams() {
		return parameters.stream()
				.map(Parameter::render)
				.collect(Collectors.joining(","));
	}

}
