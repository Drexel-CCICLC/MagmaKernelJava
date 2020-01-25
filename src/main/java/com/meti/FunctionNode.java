package com.meti;

import java.util.Collection;
import java.util.stream.Collectors;

public class FunctionNode implements Node {
	private final Node content;
	private final String name;
	private final Collection<? extends Parameter> parameters;
	private final Type returnType;

	public FunctionNode(String name, Type returnType, Collection<? extends Parameter> parameters, Node content) {
		this.name = name;
		this.returnType = returnType;
		this.parameters = parameters;
		this.content = content;
	}

	@Override
	public String render() {
		return returnType.render() + " " + name +
				"(" + renderParams() + ")" + content.render();
	}

	private String renderParams() {
		return parameters.stream()
				.map(Parameter::render)
				.collect(Collectors.joining(","));
	}
}
