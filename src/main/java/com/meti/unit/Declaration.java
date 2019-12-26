package com.meti.unit;

import com.meti.type.Type;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface Declaration {
	Optional<Declaration> child(String name);

	Map<String, Declaration> children();

	void define(String name, Type type, Collection<String> flags);

	void delete(String name);

	Collection<String> getFlags();

	Type getType();
}
