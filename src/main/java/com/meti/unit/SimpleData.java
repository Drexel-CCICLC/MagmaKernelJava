package com.meti.unit;

import com.meti.Aliaser;
import com.meti.Declarations;
import com.meti.IncrementAliaser;
import com.meti.TreeDeclarations;
import com.meti.type.TypeStack;

public class SimpleData implements Data {
	private final Aliaser aliaser;
	private final Declarations manager;
	private final TypeStack typeStack;

	public SimpleData() {
		this(new TreeDeclarations());
	}

	public SimpleData(Declarations manager) {
		this(new IncrementAliaser(), manager, new ListTypeStack());
	}

	public SimpleData(Aliaser aliaser, Declarations manager, TypeStack typeStack) {
		this.aliaser = aliaser;
		this.manager = manager;
		this.typeStack = typeStack;
	}

	@Override
	public Aliaser getAliaser() {
		return aliaser;
	}

	@Override
	public Declarations getManager() {
		return manager;
	}

	@Override
	public TypeStack getTypeStack() {
		return typeStack;
	}
}
