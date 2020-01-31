package com.meti;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;
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
	public OptionalInt childOrder(String name) {
		return null;
	}

	@Override
	public Optional<Type> childType(String name) {
		return Optional.empty();
	}

	@Override
	public boolean isNamed() {
		return false;
	}

	@Override
	public String render() {
		String joinedParams = joinParams();
		return returnType.render() + "(*" + name + ")(" + joinedParams + ")";
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}

	@Override
	public String render(String name) {
		return null;
	}

	private String joinParams() {
		return parameters.stream()
				.map(Type::render)
				.collect(Collectors.joining(","));
	}
}
