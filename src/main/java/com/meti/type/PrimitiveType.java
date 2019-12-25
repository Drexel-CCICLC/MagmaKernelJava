package com.meti.type;

import java.util.Optional;

public enum PrimitiveType implements Type {
	ANY(),
	INT(ANY),
	DOUBLE(ANY),
	STRING(ANY);

	private final Type parent;

	PrimitiveType() {
		this(null);
	}

	PrimitiveType(Type parent) {
		this.parent = parent;
	}

	@Override
	public Optional<Type> parent() {
		return Optional.ofNullable(parent);
	}
}
