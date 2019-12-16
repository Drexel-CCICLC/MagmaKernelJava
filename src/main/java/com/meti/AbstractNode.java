package com.meti;

public abstract class AbstractNode implements Node {
	protected final Struct struct;

	protected AbstractNode(Struct struct) {
		this.struct = struct;
	}

	@Override
	public Struct struct() {
		return struct;
	}
}
