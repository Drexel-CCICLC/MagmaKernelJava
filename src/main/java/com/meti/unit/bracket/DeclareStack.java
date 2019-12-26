package com.meti.unit.bracket;

import java.util.Stack;

@Deprecated
public class DeclareStack {
	private final Stack<String> stack;

	public DeclareStack(Stack<String> stack) {
		this.stack = stack;
	}

	public Stack<String> getStack() {
		return stack;
	}
}
