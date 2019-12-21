package com.meti.unit;

import com.meti.Aliaser;
import com.meti.SimpleAliaser;

import java.util.Stack;

public class SimpleData implements Data {
	private final Aliaser aliaser;
	private final Declarations declarations;
	private final Stack<String> stack;

	public SimpleData() {
		this(new SimpleAliaser(), new MapDeclarations(), new Stack<>());
	}

	public SimpleData(Aliaser aliaser, Declarations declarations1, Stack<String> stack) {
		this.declarations = declarations1;
		this.aliaser = aliaser;
		this.stack = stack;
	}

	public SimpleData(Declarations declarations1) {
		this(new SimpleAliaser(), declarations1, new Stack<>());
	}

	@Override
	public Stack<String> getStack() {
		return stack;
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
