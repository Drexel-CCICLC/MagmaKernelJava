package com.meti;

public abstract class AbstractNode implements Node {
	protected final Object value;
	protected final Struct struct;

	@Override
	public Object value() {
		return value;
	}

	@Override
	public Struct struct() {
		return struct;
	}

	protected AbstractNode(Struct struct, Object value) {
		this.struct = struct;
		this.value = value;
	}
}
