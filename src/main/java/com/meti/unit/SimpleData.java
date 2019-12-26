package com.meti.unit;

import com.meti.Aliaser;
import com.meti.DeclareManager;
import com.meti.SimpleAliaser;
import com.meti.type.TypeStack;
import com.meti.unit.bracket.DeclareStack;

import java.util.Stack;

public class SimpleData implements Data {
	private final Aliaser aliaser;
	private final Declarations declarations;
	private final DeclareManager manager = new DeclareManager();
	private final Stack<String> stack;
	private final TypeStack typeStack;

	public SimpleData() {
		this(new SimpleAliaser(), new MapDeclarations(), new Stack<>(), new ListTypeStack());
	}

	public SimpleData(Aliaser aliaser, Declarations declarations1, Stack<String> stack, TypeStack typeStack) {
		this.declarations = declarations1;
		this.aliaser = aliaser;
		this.stack = stack;
		this.typeStack = typeStack;
	}

	public SimpleData(Declarations declarations1) {
		this(new SimpleAliaser(), declarations1, new Stack<>(), new ListTypeStack());
	}

	@Override
	public DeclareStack getDeclareStack() {
		return null;
	}

	@Override
	public Stack<String> getStack() {
		return null;
	}

	@Override
	public Aliaser getAliaser() {
		return aliaser;
	}

	@Override
	public TypeStack getTypeStack() {
		return typeStack;
	}

	@Override
	public Declarations getDeclarations() {
		return declarations;
	}

	@Override
	public DeclareManager getManager() {
		return manager;
	}
}
