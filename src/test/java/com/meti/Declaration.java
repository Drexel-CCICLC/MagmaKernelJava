package com.meti;

import java.util.Collection;

public class Declaration {
	private final Collection<Flag> flags;
	private final String name;
	private final Type type;
	private final String value;

	public Declaration(Type type, Collection<Flag> flags, String name, String value) {
		this.type = type;
		this.flags = flags;
		this.name = name;
		this.value = value;
	}

	public boolean canAssign(Type type) {
		return this.type == type;
	}

	public boolean isImmutable() {
		return flags.contains(Flag.VAL);
	}

	public String render() {
		return value;
	}
}
