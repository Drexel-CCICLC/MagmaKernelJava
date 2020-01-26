package com.meti;

import java.util.*;
import java.util.function.Supplier;

public class Declarations {
	private final Declaration root = new Declaration("root");
	private final Stack<String> stack = new Stack<>();

	public Declaration define(Parameter parameter) {
		return define(parameter.getType(), parameter.getName());
	}

	public Declaration define(Type type, String name) {
		return current().define(type, name);
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

	public <T> T define(Type type, String name, Supplier<T> supplier) {
		define(type, name);
		stack.push(name);
		T t = supplier.get();
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
