package com.meti;

import java.util.Optional;

public abstract class AbstractNode implements Node {
	protected Struct struct;
	private Node parent = null;

	protected AbstractNode(Struct struct) {
		this.struct = struct;
	}

	@Override
	public Optional<Node> getParent() {
		return Optional.ofNullable(parent);
	}

	@Override
	public void setParent(Node parent) {
		this.parent = parent;
	}

	@Override
	public Struct struct() {
		return struct;
	}
}
