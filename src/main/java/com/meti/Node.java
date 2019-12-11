package com.meti;

public abstract class Node {
	protected final Object value;
	private final Struct struct;

	public Node(Struct struct, Object value) {
		this.struct = struct;
		this.value = value;
	}

	public abstract String compile();

	public abstract Node transform();
}
