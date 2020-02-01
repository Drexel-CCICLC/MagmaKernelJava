package com.meti.node.declare;

import com.meti.node.Parameter;
import com.meti.node.Type;
import com.meti.node.struct.FunctionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbstractDeclaration implements Declaration {
	private final List<Declaration> children = new ArrayList<>();
	private final String name;
	private final Type type;

	public AbstractDeclaration(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public Optional<Declaration> child(String name) {
		return children.stream()
				.filter(declaration -> declaration.getName().equals(name))
				.findFirst();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Declaration> children() {
		return children;
	}

	@Override
	public Parameter toParameter() {
		return Parameter.create(type, name);
	}

	@Override
	public Declaration define(Type type, String name) {
		Declaration declaration = new AbstractDeclaration(name, type);
		children.add(declaration);
		return declaration;
	}

	@Override
	public Declaration define(Parameter parameter) {
		Declaration declaration = parameter.toDeclaration();
		children.add(declaration);
		return declaration;
	}

	@Override
	public String instanceName() {
		return name + "_";
	}

	@Override
	public boolean isFunction() {
		return type instanceof FunctionType;
	}

	@Override
	public boolean isParent() {
		return children.stream().anyMatch(Declaration::isFunction);
	}
}
