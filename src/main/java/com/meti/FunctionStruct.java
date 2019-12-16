package com.meti;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

final class FunctionStruct implements Struct {
	private final Collection<Struct> parameters;
	private final Struct returnType;

	FunctionStruct(Collection<Struct> parameters, Struct returnType) {
		this.parameters = parameters;
		this.returnType = returnType;
	}

	public Struct getReturnType() {
		return returnType;
	}

	@Override
	public boolean isInstance(Struct other) {
		return false;
	}


	@Override
	public Struct merge(Struct root) {
		if (isInstance(root)) {
			return this;
		}
		throw new IllegalArgumentException(root + " is not a struct.");
	}

	@Override
	public String name() {
		return "(" +
				parameters.stream()
						.map(Struct::name)
						.collect(Collectors.joining(",")) +
				")" +
				"=>" +
				(returnType == null ? "void" : returnType.name());
	}

	@Override
	public Optional<Struct> parent() {
		return Optional.empty();
	}

	@Override
	public Optional<Node> parentNode() {
		return Optional.empty();
	}
}
