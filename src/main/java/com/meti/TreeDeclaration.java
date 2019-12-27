package com.meti;

import com.meti.exception.AlreadyExistsException;
import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;

public class TreeDeclaration implements Declaration {
	protected final Map<String, Declaration> children;
	protected final Collection<String> flags;
	protected final String name;
	protected Type type;

	public TreeDeclaration(Map<String, Declaration> children, Collection<String> flags, String name, Type type) {
		this.children = children;
		this.flags = flags;
		this.name = name;
		this.type = type;
	}

	public TreeDeclaration(Collection<String> flags, Type type, String name) {
		this(new LinkedHashMap<>(), flags, name, type);
	}

	@Override
	public Optional<Declaration> child(String name) {
		return Optional.ofNullable(children.get(name));
	}

	@Override
	public Map<String, Declaration> children() {
		return children;
	}

	@Override
	public Declaration define(String name, Type type, Collection<String> flags) {
		if (children.containsKey(name)) {
			throw new AlreadyExistsException(name + " is already defined.");
		}
		Declaration declaration = new TreeDeclaration(flags, type, name);
		children.put(name, declaration);
		return declaration;
	}

	@Override
	public void delete(String name) {
		children.remove(name);
	}

	@Override
	public Collection<String> flags() {
		return unmodifiableCollection(flags);
	}

	@Override
	public boolean hasFlag(String value) {
		return flags().contains(value);
	}

	@Override
	public boolean isMutable() {
		return hasFlag("val");
	}

	@Override
	public boolean isNative() {
		return hasFlag("native");
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public OptionalInt order(String child) {
		String[] array = children.keySet().toArray(String[]::new);
		int length = array.length;
		for (int i = 0; i < length; i++) {
			if (array[i].equals(child)) return OptionalInt.of(i);
		}
		return OptionalInt.empty();
	}

	@Override
	public Type type() {
		return type;
	}
}
