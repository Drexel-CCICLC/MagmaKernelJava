package com.meti;

import java.util.Collection;
import java.util.stream.Collectors;

public class FunctionType implements Type {
	private final String name;
	private final Collection<Type> parameters;
	private final Type returnType;

	public FunctionType(Collection<Type> parameters, Type returnType, String name) {
		this.parameters = parameters;
		this.returnType = returnType;
		this.name = name;
	}

	@Override
	public boolean isNamed() {
		return true;
	}

	@Override
	public String render() {
		String joinedParams = parameters.stream()
				.map(Type::render)
				.collect(Collectors.joining(","));
		return returnType.render() + "(*" + name + ")(" + joinedParams + ")";
	}
}
