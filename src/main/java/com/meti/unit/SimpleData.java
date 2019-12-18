package com.meti.unit;

import com.meti.Aliaser;
import com.meti.SimpleAliaser;

public class SimpleData implements Data {
	private final Aliaser aliaser;
	private final Declarations declarations;

	public SimpleData() {
		this(new SimpleAliaser(), new MapDeclarations());
	}

	public SimpleData(Aliaser aliaser, Declarations declarations1) {
		this.declarations = declarations1;
		this.aliaser = aliaser;
	}

	@Override
	public Aliaser getAliaser() {
		return aliaser;
	}

	@Override
	public Declarations getDeclarations() {
		return declarations;
	}
}
