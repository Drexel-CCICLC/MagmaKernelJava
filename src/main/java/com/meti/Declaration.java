package com.meti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Declaration {
	private final StringBuilder callback;
	private final Map<String, Declaration> children = new HashMap<>();
	private final List<Flag> flags;
	private final String name;
	private final Type type;

	public Declaration(String name, Type type, List<Flag> flags, StringBuilder callback) {
		this.name = name;
		this.type = type;
		this.flags = flags;
		this.callback = callback;
	}

	public StringBuilder callback() {
		return callback;
	}

	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	public void define(String name, Type type, List<Flag> flags, StringBuilder callback) {
		children.put(name, new Declaration(name, type, flags, callback));
	}

	public List<Flag> flags() {
		return flags;
	}

	public String name() {
		return name;
	}

	public Type type() {
		return type;
	}
}
