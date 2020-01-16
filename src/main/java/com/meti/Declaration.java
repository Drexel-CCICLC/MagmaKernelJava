package com.meti;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Declaration {
	private final Map<String, Declaration> children = new LinkedHashMap<>();
	private final String name;
	private final Type type;

	public Declaration(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	public Map<String, Type> childMap() {
		Map<String, Type> toReturn = new LinkedHashMap<>();
        children.forEach((s, declaration) -> toReturn.put(s, declaration.type));
		return toReturn;
	}

	public void define(String name, Type type) {
		children.put(name, new Declaration(name, type));
	}

	public String name() {
		return name;
	}

	public Type type() {
		return type;
	}
}
