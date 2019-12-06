package com.meti.interpret;

import java.util.List;
import java.util.Optional;

public class FunctionType implements Type {
	private final List<Type> parameters;
	private final Type returnType;

	public FunctionType(List<Type> parameters, Type returnType) {
		this.parameters = parameters;
		this.returnType = returnType;
	}

	@Override
	public Optional<String> value() {
		return Optional.empty();
	}

	@Override
	public List<Type> parameters() {
		return parameters;
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.ofNullable(returnType);
	}
}
