package com.meti;

import java.util.Optional;

import static com.meti.PrimitiveNodeFactory.PrimitiveStruct.ANY;

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
	public Struct merge(Struct root) {
		if (isInstance(root)) {
			return root;
		}
		return root.parent()
				.map(this::merge)
				.orElse(ANY);
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
