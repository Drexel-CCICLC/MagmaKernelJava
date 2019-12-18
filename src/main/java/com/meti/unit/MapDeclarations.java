package com.meti.unit;

import com.meti.exception.AlreadyExistsException;
import com.meti.exception.DoesNotExistException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDeclarations implements Declarations {
	private final Map<String, Declaration> declarations;

	public MapDeclarations() {
		this(new HashMap<>());
	}

	public MapDeclarations(Map<String, Declaration> declarations) {
		this.declarations = declarations;
	}

	@Override
	public void define(String name) {
		define(name, Collections.emptyList());
	}

	@Override
	public void define(String name, List<String> flags) {
		if (declarations.containsKey(name)) {
			throw new AlreadyExistsException("\"" + name + "\" has already been defined.");
		} else {
			declarations.put(name, new Declaration(flags));
		}
	}

	@Override
	public void delete(String name) {
		if (declarations.containsKey(name)) {
			declarations.remove(name);
		} else {
			throw new DoesNotExistException("\"" + name + "\" has not been defined.");
		}
	}

	@Override
	public boolean hasFlag(String name, String flag) {
		return isDefined(name) && declarations.get(name).flags.contains(flag);
	}

	@Override
	public boolean isDefined(String name) {
		return declarations.containsKey(name);
	}

	public class Declaration {
		private final List<String> flags;

		public Declaration(List<String> flags) {
			this.flags = flags;
		}
	}
}