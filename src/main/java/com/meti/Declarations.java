package com.meti;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Declarations {
	private final Set<Declaration> declarationSet = new HashSet<>();

	public void define(Type type, String name) {
		declarationSet.add(new Declaration(name));
	}

	public Optional<Declaration> get(String name) {
		return declarationSet.stream()
				.filter(declaration -> declaration.getName().equals(name))
				.findFirst();
	}
}
