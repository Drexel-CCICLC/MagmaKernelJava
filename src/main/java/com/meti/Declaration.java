package com.meti;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Declaration {
	private final Map<String, Declaration> children = new HashMap<>();
	private final String name;

	public Declaration(String name) {
		this.name = name;
	}

	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	public void define(String name) {
		children.put(name, new Declaration(name));
	}

	public String name() {
		return name;
	}
}
