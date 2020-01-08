package com.meti;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TreeDeclaration implements Declaration {
	private final StringBuilder callback;
	private final Map<String, Declaration> children = new HashMap<>();
	private final String name;
	private final Type type;

	public TreeDeclaration(String name, Type type, StringBuilder callback) {
		this.name = name;
		this.type = type;
		this.callback = callback;
	}

	@Override
	public StringBuilder callback() {
		return callback;
	}

	@Override
	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	@Override
	public void define(String name, Type type, StringBuilder callback) {
		Declaration child = new TreeDeclaration(name, type, callback);
		children.put(name, child);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Type type() {
		return type;
	}
}
