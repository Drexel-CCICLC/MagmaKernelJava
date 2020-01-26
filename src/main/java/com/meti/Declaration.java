package com.meti;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Declaration {
	private final Set<Declaration> children = new HashSet<>();
	private final String name;

	public Declaration(String name) {
		this.name = name;
	}

	public Optional<Declaration> child(String name) {
		return children.stream()
				.filter(declaration -> declaration.name.equals(name))
				.findFirst();
	}

	public Declaration define(Type type, String name) {
		Declaration declaration = new Declaration(name);
		children.add(declaration);
		return declaration;
	}

	public String getName() {
		return name;
	}
}
