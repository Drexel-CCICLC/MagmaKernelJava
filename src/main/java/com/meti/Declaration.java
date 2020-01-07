package com.meti;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Declaration {
	private final Map<String, Declaration> children = new HashMap<>();
	private final String name;
	private final String type;

	public Declaration(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	public void define(String name, String type) {
		children.put(name, new Declaration(name, type));
	}

	public String name() {
		return name;
	}

	public String type() {
		return type;
	}
}
