package com.meti.node.struct;

import com.meti.node.Node;
import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionNode implements Node {
	private final Node content;
	private final String name;
	private final Collection<? extends Parameter> parameters;
	private final Type returnType;

	public FunctionNode(String name, Type returnType, Node content, Parameter... parameters) {
		this(name, returnType, content, List.of(parameters));
	}

	public FunctionNode(String name, Type returnType, Node content, Collection<? extends Parameter> parameters) {
		this.name = name;
		this.returnType = returnType;
		this.parameters = parameters;
		this.content = content;
	}

	@Override
	public String render() {
		return returnType.render(name) +
				"(" + renderParams() + ")" + content.render();
	}

	private String renderParams() {
		return parameters.stream()
				.map(Parameter::render)
				.collect(Collectors.joining(","));
	}
}
