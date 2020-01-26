package com.meti;

import java.util.Collection;
import java.util.stream.Collectors;

public class FunctionType implements Type {
	private final String name;
	private final Collection<? extends Type> parameters;
	private final Type returnType;

	FunctionType(Collection<? extends Type> parameters, Type returnType, String name) {
		this.parameters = parameters;
		this.returnType = returnType;
		this.name = name;
	}

	@Override
	public String render() {
		String joinedParams = joinParams();
		return returnType.render() + "(*" + name + ")(" + joinedParams + ")";
	}

	private String joinParams() {
		return parameters.stream()
				.map(Type::render)
				.collect(Collectors.joining(","));
	}
}
