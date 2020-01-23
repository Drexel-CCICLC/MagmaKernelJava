package com.meti.node.bracket.struct;

import com.meti.node.StructType;
import com.meti.node.Type;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StructTypeImpl implements StructType {
	private final String name;
	private final List<? extends Type> parameters;
	private final Type returnType;

	public StructTypeImpl(Type returnType, String name, List<? extends Type> parameters) {
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.of(returnType);
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
		return returnType.render() + "(*" + name + ")" + "(" + joinedParams + ")";
	}

	@Override
	public String renderWithName(String name) {
		return (isNamed()) ? render() : render() + " " + name;
	}


}
