package com.meti;

import com.meti.type.PrimitiveType;
import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableCollection;

final class TreeDeclaration implements Declaration {
	private final Map<String, Declaration> children;
	private final Collection<String> flags;
	private final Type type;

	TreeDeclaration(Collection<String> flags, Type type) {
		this(flags, type, new LinkedHashMap<>());
	}

	private TreeDeclaration(Collection<String> flags, Type type, Map<String, Declaration> children) {
		this.flags = flags;
		this.type = type;
		this.children = children;
	}

	protected TreeDeclaration(Map<String, Declaration> children) {
		this(emptySet(), PrimitiveType.ANY, children);
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
		Declaration declaration = new TreeDeclaration(flags, type);
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
	public Type type() {
		return type;
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
	public OptionalInt order(String child) {
		String[] array = children.keySet().toArray(String[]::new);
		int length = array.length;
		for (int i = 0; i < length; i++) {
			if (array[i].equals(child)) return OptionalInt.of(i);
		}
		return OptionalInt.empty();
	}
}
