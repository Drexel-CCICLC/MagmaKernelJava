package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Declaration {
	protected final String name;
	protected final Type type;
	private final List<Declaration> children = new ArrayList<>();

	public Declaration(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public Optional<Declaration> child(String name) {
		return children.stream()
				.filter(declaration -> declaration.name.equals(name))
				.findFirst();
	}

	public List<Declaration> children() {
		return children;
	}

	public Declaration define(Type type, String name) {
		Declaration declaration = new Declaration(name, type);
		children.add(declaration);
		return declaration;
	}

	public Declaration define(Parameter parameter) {
		Declaration declaration = new ParameterDeclaration(parameter.getName(), parameter.getType());
		children.add(declaration);
		return declaration;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public boolean isParent() {
		return children.stream().anyMatch(declaration -> declaration.type instanceof FunctionType);
	}
}
