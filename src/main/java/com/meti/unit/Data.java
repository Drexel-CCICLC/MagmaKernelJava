package com.meti.unit;

import com.meti.Aliaser;
import com.meti.SimpleAliaser;

public class Data {
	private final Aliaser aliaser;
	private final Declarations declarations;

	public Data() {
		this(new SimpleAliaser(), new Declarations());
	}

	public Data(Aliaser aliaser, Declarations declarations1) {
		this.declarations = declarations1;
		this.aliaser = aliaser;
	}

	public Aliaser getAliaser() {
		return aliaser;
	}

	public Declarations getDeclarations() {
		return declarations;
	}
}
