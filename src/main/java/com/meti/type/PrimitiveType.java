package com.meti.type;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public enum PrimitiveType implements Type {
	ANY,
	INT(ANY),
	DOUBLE(ANY),
	STRING(ANY),
	VOID;

	private final Type parent;

	PrimitiveType() {
		this(null);
	}

	PrimitiveType(Type parent) {
		this.parent = parent;
	}

	@Override
	public Collection<Type> children(){
		return Collections.emptySet();
	}

	@Override
	public Optional<Type> parent() {
		return Optional.ofNullable(parent);
	}

	@Override
	public Optional<Type> returnType() {
		return Optional.empty();
	}
}
