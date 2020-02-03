package com.meti.node.struct;

import com.meti.node.Node;
import com.meti.node.Parameter;

import java.util.Collection;
import java.util.stream.Collectors;

public class StructNode implements Node {
	private final String name;
	private final Collection<? extends Parameter> parameters;

	public StructNode(String name, Collection<? extends Parameter> parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	@Override
	public String render() {
		String joinedParams = parameters.stream()
				.map(Parameter::render)
				.map(value -> value + ";")
				.collect(Collectors.joining());
		return "struct " + name + "{" + joinedParams + "};";
	}
}
