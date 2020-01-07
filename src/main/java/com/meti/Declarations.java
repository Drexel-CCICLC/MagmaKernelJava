package com.meti;

import java.util.Collection;
import java.util.Stack;

public class Declarations {
	private final Declaration root;
	private final Stack<String> stack;

	public Declarations() {
		this(new Stack<>(), new Declaration("root"));
	}

	public Declarations(Stack<String> stack, Declaration root) {
		this.stack = stack;
		this.root = root;
	}

	public void define(String name) {
		current().define(name);
	}

	public Declaration current() {
		return absolute(stack);
	}

	public Declaration absolute(Collection<String> names) {
		Declaration current = root;
		for (String name : names) {
			current = current.child(name)
					.orElseThrow(() -> new DoesNotExistException(String.join(",", names) + " is not defined."));
		}
		return current;
	}

	public void pop() {
		stack.pop();
	}

	public void push(String name) {
		stack.push(name);
	}

	public Stack<String> stack() {
		return stack;
	}
}
