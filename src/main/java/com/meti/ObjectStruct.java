package com.meti;

import java.util.Optional;

import static com.meti.PrimitiveNodeFactory.PrimitiveStruct.ANY;

public class ObjectStruct implements Struct {
	protected final Node parent;
	private final String name;

	public ObjectStruct(String name, Node node) {
		this.name = name;
		this.parent = node;
	}

	@Override
	public boolean isInstance(Struct other) {
		return this == other || (parent != null && parent.struct().isInstance(other));
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
	public Optional<Struct> parent() {
		return Optional.ofNullable(parent.struct());
	}

	@Override
	public Optional<Node> parentNode() {
		return Optional.of(parent);
	}
}
