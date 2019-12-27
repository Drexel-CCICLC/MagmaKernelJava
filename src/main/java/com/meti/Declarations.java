package com.meti;

import com.meti.type.Type;
import com.meti.unit.Declaration;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public interface Declarations {
	Declaration absolute(Collection<String> splitName);

	Declaration current();

	Declaration define(String name, Type type);

	void defineTemp(String tempName, Collection<String> tempFlags);

	Declaration define(String name, Type type, Collection<String> flags);

	void delete(String name);

	Declaration relative(String name);

	Optional<Declaration> relativeOptionally(String name);

	void sink(String name);

	void surface();
}
