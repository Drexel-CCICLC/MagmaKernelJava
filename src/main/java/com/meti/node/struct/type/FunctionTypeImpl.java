package com.meti.node.struct.type;

import com.meti.node.Parameter;
import com.meti.node.Type;

import java.util.Collection;
import java.util.stream.Collectors;

public class FunctionTypeImpl implements FunctionType {
	private final String name;
	private final Collection<? extends Parameter> parameters;
	private final Type returnType;

	public FunctionTypeImpl(Collection<? extends Parameter> parameters, Type returnType, String name) {
		this.parameters = parameters;
		this.returnType = returnType;
		this.name = name;
	}

	@Override
	public boolean isFunctional() {
		return true;
	}

	@Override
	public String render() {
		String joinedParams = joinParams();
		//TODO: fix implementation
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

	@Override
	public Type returnType() {
		return returnType;
	}
}
