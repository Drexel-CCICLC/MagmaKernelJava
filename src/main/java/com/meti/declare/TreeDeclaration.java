package com.meti.declare;

import com.meti.node.Type;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class TreeDeclaration implements Declaration {
	private final Map<String, Declaration> children = new LinkedHashMap<>();
	private final boolean isParameter;
	private final String name;
	private final Declaration parent;
	private final Type type;

	TreeDeclaration(String name, Type type, boolean isParameter, Declaration parent) {
		this.name = name;
		this.type = type;
		this.isParameter = isParameter;
		this.parent = parent;
	}

	@Override
	public Map<String, Type> childMap() {
		Map<String, Type> toReturn = new LinkedHashMap<>();
		children.forEach((s, declaration) -> toReturn.put(s, declaration.type()));
		return toReturn;
	}

	@Override
	public void define(String name, Type type, boolean isParameter) {
		children.put(name, new TreeDeclaration(name, type, isParameter, this));
	}

	@Override
	public boolean hasChildAsParameter(String childName) {
		Optional<Declaration> child = child(childName);
		return child.isPresent() && child.get().isParameter();
	}

	@Override
	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	@Override
	public boolean isParameter() {
		return isParameter;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Optional<Declaration> parent() {
		return Optional.ofNullable(parent);
	}

	@Override
	public Type type() {
		return type;
	}
}
