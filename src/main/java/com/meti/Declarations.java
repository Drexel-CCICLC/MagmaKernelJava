package com.meti;

import java.util.*;
import java.util.function.Function;

public class Declarations {
	private final AbstractDeclaration root = new ValueDeclaration("root", null);
	private final Stack<String> stack = new Stack<>();

	public String currentName() {
		return stack.peek();
	}

	public Declaration define(Parameter parameter) {
		return current().define(parameter);
	}

	public Declaration current() {
		return absolute(stack);
	}

	public Declaration absolute(Collection<String> stack) {
		Declaration current = root;
		for (String s : stack) {
			current = current.child(s).orElseThrow();
		}
		return current;
	}

	public void define(Type type, String name) {
		current().define(type, name);
	}

	public Declaration defineParent(Type type, String name) {
		return parent().define(type, name);
	}

	public Declaration parent() {
		return absolute(stack.subList(0, stack.size() - 1));
	}

	public Stack<String> getStack() {
		return stack;
	}

	public <T> T inStack(String name, Function<String, T> mapper) {
		stack.push(name);
		T t = mapper.apply(name);
		stack.pop();
		return t;
	}

	public boolean isRoot(Declaration declaration) {
		return root.equals(declaration);
	}

	public Optional<Declaration> parent(String name) {
		Deque<String> deque = new LinkedList<>(stack);
		while (!deque.isEmpty()) {
			Declaration declaration = absolute(deque);
			Optional<Declaration> child = declaration.child(name);
			if (child.isPresent()) {
				return Optional.of(declaration);
			} else {
				deque.pollLast();
			}
		}
		Optional<Declaration> child = root.child(name);
		return child.isPresent() ? Optional.of(root) : Optional.empty();
	}
}
