package com.meti.unit.equals.declare;

import com.meti.Type;

import java.util.Collection;

public class TreeDeclaration implements Declaration {
	private final Collection<Flag> flags;
	private final String name;
	private final Type type;
	private final String value;

	public TreeDeclaration(Type type, Collection<Flag> flags, String name, String value) {
		this.type = type;
		this.flags = flags;
		this.name = name;
		this.value = value;
	}

	@Override
	public boolean canAssign(Type type) {
		return this.type == type;
	}

	@Override
	public boolean isImmutable() {
		return flags.contains(Flag.VAL);
	}

	@Override
	public String render() {
		return value;
	}
}
