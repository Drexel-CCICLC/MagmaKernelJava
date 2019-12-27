package com.meti.unit;

import com.meti.type.Type;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public interface Declaration {
	Optional<Declaration> child(String name);

	Map<String, Declaration> children();

	Declaration define(String name, Type type, Collection<String> flags);

	void delete(String name);

	Collection<String> flags();

	Type type();

	boolean hasFlag(String value);

	boolean isMutable();

	boolean isNative();

	OptionalInt order(String child);
}
