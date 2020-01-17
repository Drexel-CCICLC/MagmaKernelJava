package com.meti;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Declaration {
	private final Map<String, Declaration> children = new LinkedHashMap<>();
	private final boolean isParameter;
	private final String name;
	private final Declaration parent;
	private final Type type;

	public Declaration(String name, Type type, boolean isParameter, Declaration parent) {
		this.name = name;
		this.type = type;
		this.isParameter = isParameter;
		this.parent = parent;
	}

	public Map<String, Type> childMap() {
		Map<String, Type> toReturn = new LinkedHashMap<>();
		children.forEach((s, declaration) -> toReturn.put(s, declaration.type));
		return toReturn;
	}

	public void define(String name, Type type, boolean isParameter) {
		children.put(name, new Declaration(name, type, isParameter, this));
	}

	public boolean hasChildAsParameter(String childName) {
		Optional<Declaration> child = child(childName);
		return child.isPresent() && child.get().isParameter();
	}

	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	public boolean isParameter() {
		return isParameter;
	}

	public String name() {
		return name;
	}

	public Optional<Declaration> parent() {
		return Optional.ofNullable(parent);
	}

	public Type type() {
		return type;
	}
}
