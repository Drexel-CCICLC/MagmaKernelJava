package com.meti;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Declarations {
	private final Declaration root = new Declaration("root", null, false, null);
	private final Stack<String> stack = new Stack<>();

	public Node define(String name, Type type, Supplier<? extends Node> action) {
		define(name, type, false);
		stack.push(name);
		Node result = action.get();
		stack.pop();
		return result;
	}

	public void define(String name, Type type, boolean isParameter) {
		current().define(name, type, isParameter);
	}

	public Declaration current() {
		return absolute(stack);
	}

	private Declaration absolute(Iterable<String> values) {
		Declaration current = root;
		for (String value : values) {
			current = current.child(value)
					.orElseThrow();
		}
		return current;
	}

	public Optional<Declaration> relative(String value) {
		Deque<String> reverseStack = new LinkedList<>(stack);
		while (!reverseStack.isEmpty()) {
			Optional<Declaration> optional = absolute(reverseStack).child(value);
			if (optional.isPresent()) {
				return optional;
			} else {
				reverseStack.pollLast();
			}
		}
		return root.child(value);
	}

	public Stream<Declaration> stackStream() {
		Collection<Declaration> declarations = new ArrayList<>();
		Deque<String> reverseStack = new LinkedList<>(stack);
		while (!reverseStack.isEmpty()) {
			declarations.add(absolute(reverseStack));
			reverseStack.pollLast();
		}
		declarations.add(root);
		return declarations.stream();
	}
}
