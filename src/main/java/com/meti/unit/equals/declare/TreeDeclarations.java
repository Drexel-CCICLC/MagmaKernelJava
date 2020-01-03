package com.meti.unit.equals.declare;

import com.meti.Type;

import java.util.Collection;
import java.util.Map;

public class TreeDeclarations implements Declarations {
	private final Map<String, Declaration> declarations;

	public TreeDeclarations(Map<String, Declaration> declarations) {
		this.declarations = declarations;
	}

	@Override
	public Declaration declare(Type type, Collection<Flag> flags, String name, String value) {
		Declaration declaration = new TreeDeclaration(type, flags, name, value);
		declarations.put(name, declaration);
		return declaration;
	}
}
