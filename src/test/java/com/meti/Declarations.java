package com.meti;

import java.util.Collection;
import java.util.Map;

public class Declarations {
	private final Map<String, Declaration> declarations;

	public Declarations(Map<String, Declaration> declarations) {
		this.declarations = declarations;
	}

	public Declaration declare(Type type, Collection<Flag> flags, String name, String value) {
		Declaration declaration = new Declaration(type, flags, name, value);
		declarations.put(name, declaration);
		return declaration;
	}
}
