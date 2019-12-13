package com.meti;

import java.util.Optional;

public class ObjectStruct implements Struct {
	protected final Struct parent;
	private final String name;

	public ObjectStruct(String name) {
		this(name, null);
	}

	public ObjectStruct(String name, Struct parent) {
		this.parent = parent;
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public boolean isInstance(Struct other) {
		return this == other || (parent != null && parent.isInstance(other));
	}

	@Override
	public Optional<Struct> parent() {
		return Optional.ofNullable(parent);
	}
}
