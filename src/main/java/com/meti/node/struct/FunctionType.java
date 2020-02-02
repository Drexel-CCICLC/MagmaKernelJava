package com.meti.node.struct;

import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.Collection;
import java.util.stream.Collectors;

public class FunctionType implements Type {
	private final String name;
	private final Collection<Parameter> parameters;
	private final Type returnType;

	public FunctionType(Collection<Parameter> parameters, Type returnType, String name) {
		this.parameters = parameters;
		this.returnType = returnType;
		this.name = name;
	}

	@Override
	public String render() {
		String joinedParams = joinParams();
		return returnType.render() + "(*" + name + ")(" + joinedParams + ")";
	}

	@Override
	public String render(String name) {
		return null;
	}

	private String joinParams() {
		return parameters.stream()
				.map(Parameter::render)
				.collect(Collectors.joining(","));
	}
}
