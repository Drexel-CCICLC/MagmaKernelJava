package com.meti;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;

public class Declarations {
	private final Declaration root;
	private final Stack<String> stack;

	public Declarations() {
		this(new Stack<>(), new Declaration("root", new Type("void")));
	}

	public Declarations(Stack<String> stack, Declaration root) {
		this.stack = stack;
		this.root = root;
	}

	public void defineSibling(String name, Type type) {
		parent().define(name, type);
	}

	public Declaration parent() {
		return absolute(stack.subList(0, stack.size() - 1));
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

	public Optional<Declaration> relative(String name) {
		Stack<String> queue = new Stack<>();
		if (stack.isEmpty()) {
			return root.child(name);
		} else {
			queue.addAll(stack.subList(0, stack.size() - 1));
			while (!queue.isEmpty()) {
				Declaration absolute = absolute(queue);
				Optional<Declaration> child = absolute.child(name);
				if (child.isPresent()) {
					return child;
				}
			}
			return Optional.empty();
		}
	}

	public Stack<String> stack() {
		return stack;
	}
}
