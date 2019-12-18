package com.meti.unit;

import com.meti.exception.AlreadyExistsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Declarations {
	private final Map<String, Declaration> declarations;

	public Declarations() {
		this(new HashMap<>());
	}

	public Declarations(Map<String, Declaration> declarations) {
		this.declarations = declarations;
	}

	void define(String name, List<String> flags) {
		if (declarations.containsKey(name)) {
			throw new AlreadyExistsException("\"" + name + "\" has already been defined.");
		} else {
			declarations.put(name, new Declaration(flags));
		}
	}

	boolean hasFlag(String name, String flag) {
		return isDefined(name) && declarations.get(name).flags.contains(flag);
	}

	boolean isDefined(String name) {
		return declarations.containsKey(name);
	}

	public class Declaration {
		private final List<String> flags;

		public Declaration(List<String> flags) {
			this.flags = flags;
		}
	}
}