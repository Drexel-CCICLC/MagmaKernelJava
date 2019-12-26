package com.meti.unit;

import com.meti.type.Type;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public interface Declaration {
	Optional<Declaration> child(String name);

	Map<String, Declaration> children();

	void define(String name, Type type, Collection<String> flags);

	void delete(String name);

	Collection<String> getFlags();

	Type getType();

	boolean hasFlag(String value);

	boolean isMutable();

	boolean isNative();

	OptionalInt order(String child);
}
