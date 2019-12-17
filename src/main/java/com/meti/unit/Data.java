package com.meti.unit;

import com.meti.Aliaser;
import com.meti.SimpleAliaser;

import java.util.HashSet;
import java.util.Set;

public class Data {
	private final Aliaser aliaser;
	private final Set<String> declarations;

	public Data() {
		this(new HashSet<>(), new SimpleAliaser());
	}

	public Data(Set<String> declarations, Aliaser aliaser) {
		this.declarations = declarations;
		this.aliaser = aliaser;
	}

	public Aliaser getAliaser() {
		return aliaser;
	}

	public Set<String> getDeclarations() {
		return declarations;
	}
}
