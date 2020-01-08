package com.meti;

import java.util.*;

public class Declaration {
	private final Map<String, Declaration> children = new HashMap<>();
	private final List<Flag> flags;
	private final String name;
	private final Type type;

	public Declaration(String name, Type type) {
		this(name, type, Collections.emptyList());
	}

	public Declaration(String name, Type type, List<Flag> flags) {
		this.name = name;
		this.type = type;
		this.flags = flags;
	}

	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	public void define(String name, Type type, List<Flag> flags) {
		children.put(name, new Declaration(name, type, flags));
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
